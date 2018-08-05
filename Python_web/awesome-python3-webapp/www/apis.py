'''
Created on Aug 2, 2018

@author: taopeng
'''
#!/usr/bin/env python3
# -*- coding: utf-8 -*-


' JSON API definition. '


import json, logging, inspect, functools


class APIError(Exception):
    ' the base APIError which contains error(required), data(optional) and message(optional). '
    '''
                    基础的APIError，包含错误类型(必要)，数据(可选)，信息(可选)
    '''
    def __init__(self, error, data='', message=''):
        super(APIError, self).__init__(message)
        self.error = error
        self.data = data
        self.message = message


class APIValueError(APIError):
    '''indicate the input value has error or invalid.
        表明输入数据有问题，data说明输入的错误字段
    '''
    def __init__(self, field, message=''):
        super(APIValueError, self).__init__('value:invalid', field, message)


class APIResourceNotFoundError(APIError):
    """indicate the resource was not found.  表明找不到资源，data说明资源名字"""
    def __init__(self, field, message=''):
        super(APIResourceNotFoundError, self).__init__('value:notFound', field, message)
        

class APIPermissionError(APIError):
    """indicate the api has no permission. 接口没有权限"""
    def __init__(self, message=''):
        super(APIPermissionError, self).__init__('permission:forbidden', 'permission', message)
        
#定义选取数量（每一页都会选取相应选取数量的数据库中日志出来显示）
class Page(object):
    def __init__(self, item_count, page_index=1, page_size=10):#参数依次是：数据库博客总数，初始页，一页显示博客数
        self.item_count = item_count
        self.page_size = page_size
        self.page_count = item_count // page_size + (1 if item_count % page_size > 0 else 0)#一共所需页的总数
        if (item_count == 0) or (page_index > self.page_count):#假如数据库没有博客或全部博客总页数不足一页
            self.offset = 0
            self.limit = 0
            self.page_index = 1
        else:
            self.page_index = page_index #初始页
            self.offset = self.page_size * (page_index - 1) #当前页数，应从数据库的那个序列博客开始显示
            self.limit = self.page_size #当前页数，应从数据库的那个序列博客结束像素
        self.has_next = self.page_index < self.page_count #有否下一页
        self.has_previous = self.page_index > 1 #有否上一页

    def __str__(self):
        return 'item_count: %s, page_count: %s, page_index: %s, page_size: %s, offset: %s, limit: %s' % (self.item_count, self.page_count, self.page_index, self.page_size, self.offset, self.limit)

    __repr__ = __str__