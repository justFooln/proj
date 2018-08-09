class _final(object):

    # base exception classes
    class ConstError(TypeError): pass
    class ConstCaseError(ConstError): pass

    def __init__(self):
        pass

    def __get__(self, name):
        if name not in self.__dict__:
            raise self.ConstError("Const name %s does not exist" % name)
        else:
            return self.__dict__[name]

    def __set__(self, instance, value):
        pass

    def __setattr__(self, name, value):
        if name in self.__dict__:
            raise self.ConstError('Const name %s already exists, its value is: ' %name)
        if not name.isupper():
            raise self.ConstCaseError('Const name %r is not all uppercase' % name)
        self.__dict__[name] = value


class Const(_final):
    def __init__(self, name, value):
        _final.__init__(self)

    def __get__(self, name):
        return _final.__get__(self,name)

    def __set__(self, instance, value):
        pass

    def __setattr__(self, name, value):
        _final.__setattr__(self, name, value)




# replace module entry in sys.modules[__name__] with instance of _const
# (and create additional reference to module so it's not deleted --
#  see Stack Overflow question: http://bit.ly/ff94g6)
# import sys
# _ref, sys.modules[__name__] = sys.modules[__name__], _const()
