'''
Created on Aug 7, 2018

@author: taopeng
'''
#!/usr/bin/env python3
# -*- coding: utf-8 -*-
from django.conf.global_settings import LOGGING

'''
创建监视修改重启程序
'''


import os, time, sys, subprocess
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler


def log(s):
    print('[Monitor] %s' % s)

command = ['echo', 'ok'] #重启操作文件的信息
process = None

#退出程序
def kill_process():
    global process
    if process:
        log('Kill process [%s]...' % process.pid)
        process.kill()
        process.wait()
        log('Process ended with code %s.' % process.returncode)
        process = None

#开始程序
def start_process():
    global process, command
    log('Start process %s...' % ' '.join(command))
    process = subprocess.Popen(command, stdin=sys.stdin, stdout=sys.stdout, stderr=sys.stderr)

#重启程序
def restart_process():
    kill_process()
    start_process()

#编辑MyFileSystemEventHander
class MyFileSystemEventHander(FileSystemEventHandler):

    def __init__(self, fn):
        super(MyFileSystemEventHander, self).__init__()
        self.restart = fn #导入重启函数restart_process，没括号

    def on_any_event(self, event):
        if event.src_path.endswith('.py'): #监视`.py`后缀文件发生改变
            log('Python source file changed: %s' % event.src_path)
            self.restart()


#监视
def start_watch(path, callback):
    observer = Observer()
    observer.schedule(MyFileSystemEventHander(restart_process), path, recursive=True)
    observer.start()
    log('Watching directory %s...' % path)
    start_process()
    try:
        while True:
            time.sleep(0.5)
    except KeyboardInterrupt:
        observer.stop()
    observer.join()

'''
 这里有个需要注意的地方，就是command变量，由于Python中的main方法其实可以看成是一个if语句，那么它里面设置的变量实质就是全局变量，所以在start_process里面能直接使用
 并且command在模板的前面就已经初始化过了，所以在main里面又给它重新赋值了
 
 index=1

def test():
    print('   -------2-------%s' %index)
    
def test2():
    global index
    index=3
    print('   ----3------%s' %index)
    
if __name__=='__main__' :
    index=2
    print('    -------1--------- %s' %index)
    test2()
    test()
'''

if __name__ == '__main__':
    sys.argv.append('app.py') #因为我这是从Eclipse中启动，所以直接把参数拼接进来了
    argv = sys.argv[1:] #用于在命令行取程序外部输入参数-->http://www.cnblogs.com/aland-1415/p/6613449.html
    if not argv:
        print('Usage: ./pymonitor your-script.py')
        exit(0)
    if argv[0] != 'python':  # 这里用python不要用python3除非你的系统也是用python3 XX.PY来启动python程序的
        argv.insert(0, 'python')
    command = argv #操作文件的名字及程序名
    path = os.path.abspath('.')#根据这监视路径,那文件就得放在监视文件的相同路径上
    start_watch(path, None)