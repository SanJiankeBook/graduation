'''
Created on Jul 31, 2018

@author: taopeng
'''
# -*- coding: utf-8 -*-
import logging; logging.basicConfig(level=logging.INFO) 
import asyncio


@asyncio.coroutine
def wget(host):
    print('wget %s...' % host)
    connect = asyncio.open_connection(host, 80)
    reader, writer = yield from connect
    logging.info(host+'第一个yield')
    header = 'GET / HTTP/1.0\r\nHost: %s\r\n\r\n' % host
    writer.write(header.encode('utf-8'))
    yield from writer.drain()
    logging.info(host+'第二个yield')
    while True:
        line = yield from reader.readline()
        logging.info(host+'第三个yield----------'+line.decode('utf-8').rstrip())
        if line == b'\r\n':
            break
        print('%s header > %s' % (host, line.decode('utf-8').rstrip()))
    # Ignore the body, close the socket
    writer.close()

loop = asyncio.get_event_loop()
tasks = [wget(host) for host in ['www.sina.com.cn', 'www.sohu.com', 'www.163.com']]
loop.run_until_complete(asyncio.wait(tasks))
loop.close()