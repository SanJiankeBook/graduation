'''
Created on Jul 31, 2018

@author: taopeng
'''
#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import asyncio, logging
import aiomysql

#记录sql操作
def log(sql, args=()):
    logging.info('SQL: %s' % sql)

#创建全局连接池,**kw关键字参数集        
async def create_pool(loop, **kw):
    logging.info('create database connection pool')
    global __pool #将__pool定义为全局变量
    __pool = await aiomysql.create_pool(#创建线程并放进——pool常量里面
        host=kw.get('127.0.0.1', 'localhost'), #主机ip，默认本机
        port=kw.get('port', 3306), #端口，默认3306
        user=kw['root'], #用户
        password=kw['root'], #用户口令
        db=kw['test'], #选择数据库
        charset=kw.get('charset', 'utf8'), #设置数据库编码，默认utf8
        autocommit=kw.get('autocommit', True), #设置自动提交事务，默认打开
        maxsize=kw.get('maxsize', 10), #设置最大连接数，默认10
        minsize=kw.get('minsize', 1), #设置最少连接数，默认1
        loop=loop #需要传递一个事件循环实例，若无特别声明，默认使用asyncio.get_event_loop()
    )

#实现SELECT语句
async def select(sql, args, size=None):
    log(sql, args) #记录sql操作
    global __pool #使用全局变量__pool
    async with __pool.acquire() as conn: #从连接池中获取一个连接，使用完后自动释放
        async with conn.cursor(aiomysql.DictCursor) as cur: #创建一个游标，返回由dict组成的list
            await cur.execute(sql.replace('?', '%s'), args or ()) #执行SQL，mysql的占位符是%s，和python一样，为了coding的便利，先用SQL的占位符？写SQL语句，最后执行时在转换过来
            if size:
                rs = await cur.fetchmany(size) #只读取size条记录
            else:
                rs = await cur.fetchall() #返回的rs是一个list，每个元素是一个dict，代表一行记录，返回所有记录
        logging.info('rows returned: %s' % len(rs))
        return rs

#实现insert\update\delete语句，默认打开自动提交事务
async def  execute(sql, args, autocommit=True):
    log(sql) #记录sql操作
    async with __pool.acquire() as conn: #获取一个连接
        if not autocommit: #如果该事物不是自动提交，那么协程就现在开始启动，如果事物出错，能直接回滚到这个位置
            await conn.begin() #协程开始启动
        try:
            async with conn.cursor(aiomysql.DictCursor) as cur: #创建一个游标，返回字典类型
                await cur.execute(sql.replace('?', '%s'), args or ()) #执行SQL
                affected = cur.rowcount #获得影响的行数
            if not autocommit:
                await conn.commit() #提交事务
        except BaseException as e:
            if not autocommit:
                await conn.rollback() #回滚当前启动的协程
            raise
        return affected

#制作参数字符串
def create_args_string(num):
    L = []
    for n in range(num): #SQL的占位符是？，num是多少就插入多少个占位符
        L.append('?')
    return ', '.join(L) #将L拼接成字符串返回，例如num=3时："?, ?, ?"

#定义数据类型的基类
class Field(object):
    def __init__(self, name, column_type, primary_key, default): #可传入参数对应列名、数据类型、主键、默认值
        self.name = name
        self.column_type = column_type
        self.primary_key = primary_key
        self.default = default
    def __str__(self): #print（Field_object）返回类名Field，数据类型，列名
        return '<%s, %s:%s>' % (self.__class__.__name__, self.column_type, self.name)

#继承Field类，定义字符类，默认变长100字节
class StringField(Field):
    def __init__(self, name=None, primary_key=False, default=None, ddl='varchar(100)'): #可传入参数列名、主键、默认值、数据类型
        super().__init__(name, ddl, primary_key, default) #对应列名、数据类型、主键、默认值

#继承Field类，定义boolean类
class BooleanField(Field):
    def __init__(self, name=None, default=False): #可传入参数列名、默认值
        super().__init__(name, 'boolean', False, default) #对应列名、数据类型、主键、默认值

#继承Field类，定义整数类(bigint)，默认值为0
class IntegerField(Field):
    def __init__(self, name=None, primary_key=False, default=0): #可传入参数列名、主键、默认值
        super().__init__(name, 'bigint', primary_key, default)

#继承Field类，定义浮点类
class FloatField(Field):
    def __init__(self, name=None, primary_key=False, default=0.0):
        super().__init__(name, 'real', primary_key, default)

#继承Field类，定义text类
class TextField(Field):
    def __init__(self, name=None, default=None):
        super().__init__(name, 'text', False, default)

#定义元类
class ModelMetaclass(type):
    def __new__(cls, name, bases, attrs): #用metaclass=ModelMetaclass创建类时，通过这个方法生成类
        if name=='Model': #如果是新创建的类是model，那么就不需要发生任何修改
            return type.__new__(cls, name, bases, attrs) #当前准备创建的类的对象、类的名字model、类继承的父类集合、类的方法集合
        tableName = attrs.get('__table__', None) or name #获取表名，默认为None，或为类名
        logging.info('found model: %s (table: %s)' % (name, tableName)) #类名、表名
        mappings = dict() #用于存储列名和对应的数据类型
        fields = [] #用于存储非主键的列
        primaryKey = None #用于主键查重，默认为None
        for k, v in attrs.items(): #遍历attrs方法集合
            if isinstance(v, Field): #判断该方法是不是类中的属性
                logging.info('  found mapping: %s ==> %s' % (k, v))
                mappings[k] = v #存储列名和数据类型
#                 if v.primary_key: #查找主键和查重，有重复则抛出异常
#                     if primaryKey:
#                         raise StandardError('Duplicate primary key for field: %s' % k)
#                     primaryKey = k
#                 else:
#                     fields.append(k) #存储非主键的列名
#         if not primaryKey: #整个表不存在主键时抛出异常
#             raise StandardError('Primary key not found.')
        for k in mappings.keys(): #过滤掉列，只剩方法
            attrs.pop(k)
        escaped_fields = list(map(lambda f: '`%s`' % f, fields)) #给非主键列加``（可执行命令）区别于''（字符串效果）   lambda()一个隐式hanshu
        attrs['__mappings__'] = mappings #保持属性和列的映射关系
        attrs['__table__'] = tableName #表名
        attrs['__primary_key__'] = primaryKey #主键属性名
        attrs['__fields__'] = fields #除主键外的属性名
        attrs['__select__'] = 'select `%s`, %s from `%s`' % (escaped_fields, tableName) #构造select执行语句，查整个表
        attrs['__insert__'] = 'insert into `%s` (%s, `%s`) values (%s)' % (tableName, ', '.join(escaped_fields), primaryKey, create_args_string(len(escaped_fields) + 1)) #构造insert执行语句，？作为占位符
        attrs['__update__'] = 'update `%s` set %s where `%s`=?' % (tableName, ', '.join(map(lambda f: '`%s`=?' % (mappings.get(f).name or f), fields)), primaryKey) #构造update执行语句，根据主键值更新对应一行的记录，？作为占位符，待传入更新值和主键值
        attrs['__delete__'] = 'delete from `%s` where `%s`=?' % (tableName, primaryKey) #构建delete执行语句，更加主键值删除对应行
        return type.__new__(cls, name, bases, attrs) #当前准备创建的类的对象、类的名字model、类继承的父类集合、类的方法集合

#定义Model类，模板类，继承dict的属性，继续元类获得属性和列的映射关系，即ORM
class Model(dict, metaclass=ModelMetaclass):
    #没__new__()，会使用父类ModelMetaclass的__new__()来生成类
    def __init__(self, **kw):
        super(Model, self).__init__(**kw)
    def __getattr__(self, key): #getattr、settattr实现属性动态绑定和获取
        try:
            return self[key]
        except KeyError:
            raise AttributeError(r"'Model' object has no attribute '%s'" % key)
    def __setattr__(self, key, value):
        self[key] = value
    def getValue(self, key): #返回属性值，默认None
        return getattr(self, key, None)
    def getValueOrDefault(self, key): #返回属性值，空则返回默认值
        value = getattr(self, key, None)
        if value is None:
            field = self.__mappings__[key] #查取属性对应的列的数量类型默认值
            if field.default is not None:
                value = field.default() if callable(field.default) else field.default
                logging.debug('using default value for %s: %s' % (key, str(value)))
                setattr(self, key, value)
        return value
    @classmethod #添加类方法，对应查表，默认查整个表，可通过where limit设置查找条件
    async def findAll(cls, where=None, args=None, **kw):
        ' find objects by where clause. '
        sql = [cls.__select__] #用一个列表存储select语句
        if where: #添加where条件
            sql.append('where')
            sql.append(where)
        if args is None:
            args = []
        orderBy = kw.get('orderBy', None) #对查询结果排序排序
        if orderBy:
            sql.append('order by')
            sql.append(orderBy)
        limit = kw.get('limit', None) #截取查询结果
        if limit is not None:
            sql.append('limit')
            if isinstance(limit, int): #截取前limit条记录
                sql.append('?')
                args.append(limit)
            elif isinstance(limit, tuple) and len(limit) == 2: #略过前limit[0]条记录，开始截取limit[1]条记录
                sql.append('?, ?')
                args.extend(limit) #将limit合并到args列表的末尾
            else:
                raise ValueError('Invalid limit value: %s' % str(limit))
        rs = await select(' '.join(sql), args) #构造更新后的select语句，并执行，返回属性值[{},{},{}]
        return [cls(**r) for r in rs] #返回一个列表,每个元素为每行记录作为一个dict传入当前类的对象的返回值
    @classmethod #添加类方法，查找特定列，可通过where设置条件
    async def findNumber(cls, selectField, where=None, args=None):
        ' find number by select and where. '
        sql = ['select %s _num_ from `%s`' % (selectField, cls.__table__)] # _num_ SQL的一个字段别名用法，AS关键字可以省略
        if where: #添加where字段
            sql.append('where')
            sql.append(where)
        rs = await select(' '.join(sql), args, 1) #更新select语句并执行，返回由dict组成的list
        if len(rs) == 0:
            return None
        return rs[0]['_num_'] #根据别名key取值
    @classmethod # 类方法，更加primary key查询一条记录
    async def find(cls, pk):
        ' find object by primary key. '
        rs = await select('%s where `%s`=?' % (cls.__select__, cls.__primary_key__), [pk], 1)
        if len(rs) == 0:
            return None
        return cls(**rs[0]) #将dict作为关键字参数传入当前类的对象
    async def save(self): #实例方法，映射插入记录
        args = list(map(self.getValueOrDefault, self.__fields__)) #非主键列的值列表
        args.append(self.getValueOrDefault(self.__primary_key__)) #添加主键值
        rows = await execute(self.__insert__, args) #执行insert语句
        if rows != 1:
            logging.warn('failed to insert record: affected rows: %s' % rows)
    async def update(self): #实例方法，映射更新记录
        args = list(map(self.getValue, self.__fields__))
        args.append(self.getValue(self.__primary_key__))
        rows = await execute(self.__update__, args)
        if rows != 1:
            logging.warn('failed to update by primary key : affected rows: %s' % rows)
    async def remove(self): #实例方法，映射根据主键值删除记录
        args = [self.getValue(self.__primary_key__)]
        rows = await execute(self.__delete__, args)
        if rows != 1:
            logging.warn('failed to remove by primary key: affected rows: %s' % rows)

# 下面实际操作一下，先定义个User类来对应数据库的表User
class User(Model):
    # 定义类的属性到列的映射
    id = IntegerField("id")
    name = StringField("username")
    email = StringField("email")
    password = StringField("password")


if __name__ == "__main__":
# 创建一个实例
    u = User(id=12345, name="ReedSun", email="sunhongzhao@foxmail.com", password="nicaicai")
    u.save()