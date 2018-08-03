'''
Created on Aug 1, 2018

@author: taopeng
'''
import time, uuid
from orm import Model, StringField, BooleanField, FloatField, TextField

def next_id(): #生成一个主键id
    return '%015d%s000' % (int(time.time() * 1000), uuid.uuid4().hex)

class User(Model):
    __table__ = 'users' #表名
    id = StringField(primary_key=True, default=next_id, ddl='varchar(50)') #主键
    email = StringField(ddl='varchar(50)') #作为登陆账号
    passwd = StringField(ddl='varchar(50)') #列
    admin = BooleanField() #列
    name = StringField(ddl='varchar(50)') #列
    image = StringField(ddl='varchar(500)') #列
    created_at = FloatField(default=time.time) #列

class Blog(Model):
    __table__ = 'blogs'
    id = StringField(primary_key=True, default=next_id, ddl='varchar(50)')
    user_id = StringField(ddl='varchar(50)')
    user_name = StringField(ddl='varchar(50)')
    user_image = StringField(ddl='varchar(500)')
    name = StringField(ddl='varchar(50)')
    summary = StringField(ddl='varchar(200)')
    content = TextField()
    created_at = FloatField(default=time.time)

class Comment(Model):
    __table__ = 'comments'
    id = StringField(primary_key=True, default=next_id, ddl='varchar(50)')
    blog_id = StringField(ddl='varchar(50)')
    user_id = StringField(ddl='varchar(50)')
    user_name = StringField(ddl='varchar(50)')
    user_image = StringField(ddl='varchar(500)')
    content = TextField()
    created_at = FloatField(default=time.time)


