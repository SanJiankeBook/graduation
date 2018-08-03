'''
Created on Aug 2, 2018

@author: taopeng
'''
#!/usr/bin/env python3
# -*- coding: utf-8 -*-


'''
Default configurations.
模板
开发环境配置
把config_default.py作为开发环境的标准配置，把config_override.py作为生产环境的标准配置，我们就可以既方便地在本地开发，又可以随时把应用部署到服务器上。
'''


configs = {
    'debug': True,
    'db': {
        'host': '127.0.0.1',
        'port': 3306,
        'user': 'root',
        'password': 'root',
        'db': 'test'
    },
    'session': {
        'secret': 'AWESOME'
    }
}
