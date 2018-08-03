#!/usr/bin/env python3
# -*- coding: utf-8 -*-


'''
Configuration
'''


import config_default
import logging; logging.basicConfig(level=logging.INFO)

class Dict(dict):
    ' Simple dict but support access as x.y style. '
    def __init__(self, names=(), values=(), **kw):
        super().__init__(**kw)
        for k, v in zip(names, values):
            self[k] = v
    def __getattr__(self, key):
        try:
            return self[key]
        except KeyError:
            raise AttributeError(r"'Dict' object has no attribute '%s'" % key)
    def __setattr__(self, key, value):
        self[key] = value

'''
 这个方法巧妙的使用递归来解决了数据分层问题，其中isinstance（v，dict）是关键，通过判断一个值是否是字典，如果是字典那么就接着递归，否则就直接拿override里面的值
'''
def merge(defaults, override):
    ' 读取更新的配置 '
    r = {}
    for k, v in defaults.items(): #迭代出默认配置信息
        if k in override: #如果默认配置里面的这个键在新配置里面存在，那么就证明这个键里面的值是需要改的
            if isinstance(v, dict): #判断这个键对应的值是否是字典类型，即是否嵌套
                r[k] = merge(v, override[k]) #如果嵌套，那么就递归一次
            else:
                r[k] = override[k] #否则直接把新的值写入字典就行了
        else:
            r[k] = v #如果在新配置里面不存在该键，那么进行该操作
    return r  #返回结果

'''
a, b, c = 1, 2, 3
if a>b:

    c = a

else:

    c = b
    
    c = a if a>b else b  一行表达式,为真时放if前
'''

def toDict(d):
    ' 转换成Dict类 '
    D = Dict()
    for k, v in d.items():
        D[k] = toDict(v) if isinstance(v, dict) else v  #类似三目运算符
    return D

'''
应用程序读取配置文件需要优先从config_override.py读取。为了简化读取配置文件，可以把所有配置读取到统一的config.py中：
'''
configs = config_default.configs #取到默认配置的文件
try:
    import config_override  #引入重载的配置文件，可能改配置文件没有，所以在这里使用异常检查
    configs = merge(configs, config_override.configs)  #读取新的配置
except ImportError:
    pass
# print(cofigs.__class__)
# print(cofigs.get('debug'))
configs = toDict(configs) # configs.attr  将配置类里面的属性值可以通过.attr形式输出，不然就只能使用dict特定的get（）方式输出了
# print(configs.__class__)
# print(cofigs.get('debug'))