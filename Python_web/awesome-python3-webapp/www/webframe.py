'''
Created on Aug 1, 2018

@author: taopeng
'''
#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import asyncio, os, inspect, logging, functools

from urllib import parse
from aiohttp import web
from apis import APIError

'''
Python装饰器（decorator）在实现的时候，被装饰后的函数其实已经是另外一个函数了（函数名等函数属性会发生改变），
为了不影响，Python的functools包中提供了一个叫wraps的decorator来消除这样的副作用。写一个decorator的时候，
最好在实现之前加上functools的wrap，它能保留原有函数的名称和docstring。 
'''

def get(path):
    ' @get装饰器，给处理函数绑定URL和HTTP method-GET的属性 '
    def decorator(func):    #装饰器
        @functools.wraps(func)
        def wrapper(*args, **kw):
            return func(*args, **kw)
        wrapper.__method__ = 'GET'
        wrapper.__route__ = path
        return wrapper
    return decorator


def post(path):
    ' @post装饰器，给处理函数绑定URL和HTTP method-POST的属性 '
    def decorator(func):
        @functools.wraps(func)
        def wrapper(*args, **kw):
            return func(*args, **kw)
        wrapper.__method__ = 'POST'
        wrapper.__route__ = path
        return wrapper
    return decorator


def has_request_arg(fn):
    ' 检查函数是否有request参数，返回布尔值。若有request参数，检查该参数是否为该函数的最后一个参数，否则抛出异常 '
    params = inspect.signature(fn).parameters # 含有 参数名，参数 的信息
    found = False
    for name, param in params.items():
        if name == 'request':
            found = True
            continue #退出本次循环
        #如果找到‘request’参数后，还出现位置参数，就会抛出异常
        if found and (param.kind != inspect.Parameter.VAR_POSITIONAL and param.kind != inspect.Parameter.KEYWORD_ONLY and param.kind != inspect.Parameter.VAR_KEYWORD):
            raise ValueError('request parameter must be the last named parameter in function: %s%s' % (fn.__name__, str(sig)))
    return found


def has_var_kw_arg(fn):
    ' 检查函数是否有关键字参数集，返回布尔值 '
    params = inspect.signature(fn).parameters
    for name, param in params.items():
        if param.kind == inspect.Parameter.VAR_KEYWORD:
            return True


def has_named_kw_args(fn):
    ' 检查函数是否有命名关键字参数，返回布尔值 '
    params = inspect.signature(fn).parameters
    for name, param in params.items():
        if param.kind == inspect.Parameter.KEYWORD_ONLY:
            return True


def get_named_kw_args(fn):
    ' 将函数所有的 命名关键字参数名 作为一个tuple返回 '
    args = []
    params = inspect.signature(fn).parameters
    for name, param in params.items():
        if param.kind == inspect.Parameter.KEYWORD_ONLY:
            args.append(name)
    return tuple(args)


def get_required_kw_args(fn):
    ' 将函数所有 没默认值的 命名关键字参数名 作为一个tuple返回 '
    args = []
    params = inspect.signature(fn).parameters #An ordered mapping of parameters' names to the corresponding Parameter object.
    for name, param in params.items():

        if param.kind == inspect.Parameter.KEYWORD_ONLY and param.default == inspect.Parameter.empty:
            # param.kind : describes how argument values are bound to the parameter. 描述参数值如何绑定到参数
            # KEYWORD_ONLY : value must be supplied as keyword argument, which appear after a * or *args 值必须作为关键字参数提供，它出现在a或args之后
            # param.default : the default value for the parameter,if has no default value,this is set to Parameter.empty 参数的默认值，如果没有默认值，则设置为参数
            # Parameter.empty : a special class-level marker to specify absence of default values and annotations
            args.append(name)
    return tuple(args)


class RequestHandler(object):
    ' 请求处理器，用来封装处理函数 '
    def __init__(self, app, fn):
        # app : an application instance for registering the fn 用于注册函数的应用程序实例
        # fn : a request handler with a particular HTTP method and path 带有特定HTTP方法和路径的请求处理程序
        self._app = app
        self._func = fn
        self._has_request_arg = has_request_arg(fn) # 检查函数是否有request参数
        self._has_var_kw_arg = has_var_kw_arg(fn) # 检查函数是否有关键字参数集
        self._has_named_kw_args = has_named_kw_args(fn) # 检查函数是否有命名关键字参数
        self._named_kw_args = get_named_kw_args(fn) # 将函数所有的 命名关键字参数名 作为一个tuple返回
        self._required_kw_args = get_required_kw_args(fn) # 将函数所有 没默认值的 命名关键字参数名 作为一个tuple返回

    async def __call__(self, request):
        ' 分析请求，request handler,must be a coroutine that accepts a request instance as its only argument and returns a streamresponse derived instance '
        '请求处理程序，必须是一个coroutine，它接受一个请求实例作为它唯一的参数，并返回一个streamresponse派生实例'
        kw = None
        if self._has_var_kw_arg or self._has_named_kw_args or self._required_kw_args:
            # 当传入的处理函数具有 关键字参数集 或 命名关键字参数 或 request参数
            if request.method == 'POST':
                # POST请求预处理
                if not request.content_type:
                    # 无正文类型信息时返回
                    return web.HTTPBadRequest('Missing Content-Type.')
                ct = request.content_type.lower()
                if ct.startswith('application/json'):
                    # 处理JSON类型的数据，传入参数字典中
                    params = await request.json()
                    if not isinstance(params, dict):
                        return web.HTTPBadRequest('JSON body must be object.')
                    kw = params
                elif ct.startswith('application/x-www-form-urlencoded') or ct.startswith('multipart/form-data'):
                    # 处理表单类型的数据，传入参数字典中
                    params = await request.post()
                    kw = dict(**params)
                else:
                    # 暂不支持处理其他正文类型的数据
                    return web.HTTPBadRequest('Unsupported Content-Type: %s' % request.content_type)
            if request.method == 'GET':
                # GET请求预处理
                qs = request.query_string
                # 获取URL中的请求参数，如 name=Justone, id=007
                if qs:
                    # 将请求参数传入参数字典中
                    kw = dict()
                    for k, v in parse.parse_qs(qs, True).items():
                        # parse a query string, data are returned as a dict. the dict keys are the unique query variable names and the values are lists of values for each name
                        # a True value indicates that blanks should be retained as blank strings
                        kw[k] = v[0]
        if kw is None:
            # 请求无请求参数时
            kw = dict(**request.match_info)
            # Read-only property with AbstractMatchInfo instance for result of route resolving
        else:
            # 参数字典收集请求参数
            if not self._has_var_kw_arg and self._named_kw_args:
                copy = dict()
                for name in self._named_kw_args:
                    if name in kw:
                        copy[name] = kw[name]
                kw = copy
            for k, v in request.match_info.items():
                if k in kw:
                    logging.warning('Duplicate arg name in named arg and kw args: %s' % k)
                kw[k] = v
        if self._has_request_arg:
            kw['request'] = request
        if self._required_kw_args:
            # 收集无默认值的关键字参数
            for name in self._required_kw_args:
                if not name in kw:
                    # 当存在关键字参数未被赋值时返回，例如 一般的账号注册时，没填入密码就提交注册申请时，提示密码未输入
                    return web.HTTPBadRequest('Missing arguments: %s' % name)
        logging.info('call with args: %s' % str(kw))
        try:
            r = await self._func(**kw)
            # 最后调用处理函数，并传入请求参数，进行请求处理
            return r
        except APIError as e:
            return dict(error=e.error, data=e.data, message=e.message)


def add_static(app):
    ' 添加静态资源路径 '
    path = os.path.join(os.path.dirname(os.path.abspath(__file__)), 'static') #获得包含'static'的绝对路径
    # os.path.dirname(os.path.abspath(__file__)) 返回脚本所在目录的绝对路径
    app.router.add_static('/static/', path) # 添加静态资源路径
    logging.info('add static %s => %s' % ('/static/', path))


def add_route(app, fn):
    ' 将处理函数注册到web服务程序的路由当中 '
    method = getattr(fn, '__method__', None) # 获取 fn 的 __method__ 属性的值，无则为None
    path = getattr(fn, '__route__', None) # 获取 fn 的 __route__ 属性的值，无则为None
    if path is None or method is None:
        raise ValueError('@get or @post not define in %s.' % str(fn))
    if not asyncio.iscoroutinefunction(fn) and not inspect.isgeneratorfunction(fn):
        # 当处理函数不是协程时，封装为协程函数
        fn = asyncio.coroutine(fn)
    logging.info('add route %s %s => %s(%s)' % (method, path, fn.__name__, ', '.join(inspect.signature(fn).parameters.keys())))
    app.router.add_route(method, path, RequestHandler(app, fn))


def add_routes(app, module_name):
    ' 自动把handler模块符合条件的函数注册 '
    n = module_name.rfind('.')   #-1 
    if n == (-1):
        # 没有匹配项时
        mod = __import__(module_name, globals(), locals())
        # import一个模块，获取模块名 __name__
    else:
        # 添加模块属性 name，并赋值给mod
        name = module_name[n+1:]
        mod = getattr(__import__(module_name[:n], globals(), locals(), [name]), name)
    for attr in dir(mod):
        # dir(mod) 获取模块所有属性
        if attr.startswith('_'):
            # 略过所有私有属性
            continue
        fn = getattr(mod, attr)
        # 获取属性的值，可以是一个method
        if callable(fn): #判断该函数是否可以被调用
            method = getattr(fn, '__method__', None)
            path = getattr(fn, '__route__', None)
            if method and path:
                # 对已经修饰过的URL处理函数注册到web服务的路由中
                add_route(app, fn)