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
 