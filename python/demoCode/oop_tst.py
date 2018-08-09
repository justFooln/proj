    class Employee:
        num_of_emps = 0     #Class variable - equ static
        raise_amount = 1.04 #Class variable - equ static

        def __init__(self, f, l, p):
            self.first =                #Instance
            self.last = l               #Instance
            self.pay = p                #Instance
            self.email = first + '.' + last + '@company.com'  #Instance
            Employee.num_of_emps += 1   #Class var

        def fullname(self):
            return '{} {}'.format(self.first, self.last)

        def apply_raise(self):
            self.pay = int(self.pay * Employee.raise_amount)

        @classmethod
        def set_raise_amt(cls,amount):  #Can't use class keyword
            cls.raise_amt = amount
    
        @classmethod
        def from_string(cls, str)
            first, last, pay = str.split('-')
            pay_num = int(pay)
            tmp_cls = cls(first, last, pay_num)
            tmp_cls.num_of_emps += 1   #Class var
            return tmp_cls
            # what about setting self.email & Employee.num_of_emps?
            
