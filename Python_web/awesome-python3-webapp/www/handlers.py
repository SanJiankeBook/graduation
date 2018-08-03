'''
Created on Aug 2, 2018

@author: taopeng
'''
#!/usr/bin/env python3
# -*- coding: utf-8 -*-


' url handlers '


from webframe import get, post
from models import User, Blog, Comment, next_id
import time, json, hashlib
import re, logging; logging.basicConfig(level=logging.INFO)
from apis import APIError, APIValueError
from aiohttp import web
from config import configs



global COOKIE_NAME
COOKIE_NAME = 'awesession' #用来在set_cookie中命名

global _COOKIE_KEY 
_COOKIE_KEY= configs['session']['secret']#导入默认设置


_reEmail = re.compile(r'^[0-9a-z\.\-\_]+\@[0-9a-z\-\_]+(\.[0-9a-z\-\_]+){1,4}$')
_reSha1 = re.compile(r'^[0-9a-f]{40}$') # SHA1不够安全，后续需升级

#检测有否登录且是否为管理员
def check_admin(request):
    if request.__user__ is None or request.__user__.admin:
        raise APIPermissionError()
    
    
def text2html(text):
    '''将文本拼接成html格式文件'''# filter将所有文本根据每行数据挑选出来，并且那种一行都为空的数据不要，之后通过map将这些值全部作用到匿名函数中
    lines = map(lambda s: '<p>%s</p>' % s.replace('&', '&amp;').replace('<', '&lt;').replace('>', '&gt;'), filter(lambda s: s.strip() != '', text.split('\n')))
    return ''.join(lines)

def get_page_index(page_str):#获取当前页索引
    p = 1
    try:
        p = int(page_str)
    except ValueError as e:
        pass
    if p < 1:
        p = 1
    return p
'''
查看上一章的代码，并不难发现当一个正确的密码输入，会经历以下步骤：

首先会由网页中的javascript取得对应的值，并按照如下组合方式，进行摘要算法计算出一个字符串(A)：

`"email" + ":" + "passwd"`

然后字符串(A)被以密码的形式传递到API内，在API内，字符串(A)再一次按照如下组合方式，进行摘要算法计算出一个字符串(B)，并保存到服务器数据库上。

`"用户id" + ":" + 字串符(A)`

到了最后制作cookie发送给浏览器时，又使用字符串(B)按照如下组合方式，进行摘要算法计算出一个字符串(C)：

`"用户id" + 字串符(B) + "到期时间" + "密匙"`

最后再按照如下组合方式，生成一个字串符(D)发送给浏览器

`"用户id" + "到期时间" + 字符串(C)`

所以浏览器实际收到的信息有：用户id、过期时间、SHA1值(字串符(C)) 
在cookie未到过期的期间，当服务器验证cookie是否伪造时，其实只需根据用户信息在数据库查找相应的用户口令(即字符串(B))，再使用其进行摘要算法与cookie中的字符串(C)比较是否等价，就可以知道是否伪造了。
'''

def user2cookie(user, max_age): #利用hashlib对数据进行加密
    """加密cookie"""
    expires = str(int(time.time() + max_age)) # 计算过期时间，以字符串返回
    s = '%s-%s-%s-%s' %(user.id, user.passwd, expires, _COOKIE_KEY)
    L = [user.id, expires, hashlib.sha1(s.encode('utf-8')).hexdigest()]
    return '-'.join(L)


async def cookie2user(cookie_str):
    '''解密cookie'''
    if not cookie_str:
        return None
    try:
        L = cookie_str.split('-')
        if len(L) != 3:
            return None
        uid, expires, sha1 = L
        if int(expires) < time.time():#如果发现过期时间小于现在时间，即该cookie已经过期了
            # cookie过期
            return None
        user = await User.find(uid)
        if user is None:
            return None  #重新拼接cookie，进行比较
        s = '%s-%s-%s-%s' % (uid, user.passwd, expires, _COOKIE_KEY)
        if sha1 != hashlib.sha1(s.encode('utf-8')).hexdigest():
            logging.info('该cookie无效')
            return None
        user.passwd = '******' #保密
        return user
    except Exception as e:
        logging.exception(e)
        return None





@get('/')  # 调用了get装饰器
def index(request):
    summary = '成长，是一段渐行渐远的分离。'
    blogs = [
        Blog(id='1', name='Test Blog', summary=summary, created_at=time.time()-120),
        Blog(id='2', name='Something New', summary=summary, created_at=time.time()-3600),
        Blog(id='3', name='Learn Swift', summary=summary, created_at=time.time()-7200)
    ]
    return {
        '__template__': 'blogs.html',
        'blogs': blogs,
        '__user__': request.__user__
    }
    
@get('/api/users')
async def api_get_users():
    users = await User.findAll(orderBy='created_at desc')
    for u in users:
        u.passwd = '******'
    return dict(users=users)

@get('/register')
def register():
    return { '__template__': 'register.html'}


@get('/signin')
def signin():
    return { '__template__': 'signin.html'}


@post('/api/users') #用户注册
async def api_register_user(*, email, name, passwd):
    """kw var : email, name, passwd"""
    if not email or not _reEmail.match(email): #判断该邮箱是否符合自己的规范
        raise APIValueError('email')
    if not name or not name.strip(): # 移除字符串头尾的空格
        raise APIValueError('name') #如果名字是空格或没有返错，这里感觉not name可以省去，因为在web框架中的RequsetHandler已经验证过一遍了
    if not passwd or not _reSha1.match(passwd):
        raise APIValueError('passwd')
    users = await User.findAll('email=?', [email]) # 对应 where, args 参数 #查询邮箱是否已注册，查看ORM框架源码
    if len(users) > 0:
        raise APIError('注册失败', email, '邮箱已经被使用')
    uid = next_id() #随机生成一个主键
    sha1Passwd = '%s:%s' % (uid, passwd) #进行密码加密 ,密码再加密
    user = User(id=uid, email=email, passwd=hashlib.sha1(sha1Passwd.encode('utf-8')).hexdigest(), name=name.strip(), image='about:blank')
    await user.save() #进行注册
    # cookie的制作
    r = web.Response() #设置cookie
    r.set_cookie(COOKIE_NAME, user2cookie(user, 86400), max_age=86400, httponly=True) # httponly指定JS不能获取COOKIE
    user.passwd = '******' # 清理内存中的passwd
    r.content_type = 'application/json'
    r.body = json.dumps(user, ensure_ascii=False).encode('utf-8') # 转换成JSON格式
    return r


@post('/api/authenticate') #用户登录校验
async def authenticate(*, email, passwd):
    if not email: #判断该邮箱是否符合规定
        raise APIValueError('email', 'Invalid email.')
    if not passwd: #判断密码是否输入
        raise APIValueError('passwd', 'Invalid password')
    users = await User.findAll('email=?', [email])#根据邮箱得到用户id，接下来的步骤能用上
    if len(users) == 0: #判断该用户是否存在
        raise APIValueError('email', 'Email is not existed.')
    user = users[0]
    sha1 = hashlib.sha1()#更新哈希对象以字符串参数。如果同一个hash对象重复调用该方法，m.update(a); m.update(b) 等价于 m.update(a+b)
    sha1.update(user.id.encode('utf-8')) #重新拼接
    sha1.update(b':')
    sha1.update(passwd.encode('utf-8'))
    if user.passwd != sha1.hexdigest():#判断是否相等
        raise APIValueError('passwd', 'Invalid password.')
    # authenticate ok, set cookie
    r = web.Response() #编辑返回数据
    r.set_cookie(COOKIE_NAME, user2cookie(user, 86400), max_age=86400, httponly=True) # httponly指定JS不能获取COOKIE
    user.passwd = '******' # 清理内存中的passwd
    r.content_type = 'application/json'
    r.body = json.dumps(user, ensure_ascii=False).encode('utf-8') # 转换成JSON格式
    return r

@get('/signout')
def signout(request):
    '''用户退出，将SESSION中的用户信息设置为无效，返回当前页面或首页'''
    referer = request.headers.get('Referer') # 获取当前URL
    resp = web.HTTPFound(referer or '/')
    resp.set_cookie(COOKIE_NAME, '-signout-', max_age=0, httponly=True)
    # 也可以直接清除
    # resp.del_cookie(COOKIE_NAME)
    logging.info('user: %s signout.' % request.__user__.name)
    return resp

@get('/blog/{blog_id}')#查看日志
async def viewBlog(*, blog_id):
    '''查阅一篇日志'''
    blog = await Blog.find(blog_id)
    comments = await Comment.findAll('blog_id=?', [blog_id], orderBy='created_at desc')
    for comment in comments:
        comment.html_content = text2html(comment.content)
    blog.html_content = text2html(blog.content)
    return {
        '__template__': 'blogs.html',
        'blog': blog,
        'comments': comments
    }

@get('/blog/create')#添加博客
async def viewBlog():
    '''查阅一篇日志'''
    summary = '成长，是一段渐行渐远的分离。'
    blogs = [
        Blog(id='1', name='Test Blog', summary=summary, created_at=time.time()-120),
        Blog(id='2', name='Something New', summary=summary, created_at=time.time()-3600),
        Blog(id='3', name='Learn Swift', summary=summary, created_at=time.time()-7200)
    ]
    for blog in blogs:
        await blog.save()
        logging.info("插入一条博客内容为" +blog.summary)
        
