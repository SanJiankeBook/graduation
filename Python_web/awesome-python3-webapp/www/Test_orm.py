'''
Created on Aug 1, 2018

@author: taopeng
'''
import asyncio
import orm
from models import User, Blog, Comment

async def test(loop):  #测试写的models是否可以跑通      
    await orm.create_pool(user='root', password='root', db='awesome', loop=loop)
    u = User(id='0225221',name='Test10', email='test11@example.com', passwd='123456', image='about:blank')
    await u.save()

loop = asyncio.get_event_loop()
loop.run_until_complete(test(loop))
loop.close()
