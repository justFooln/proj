"""
A pythonic class definition demonstrating property, classmethods, dunder fxns, decorators
  OOP 1 - https://youtu.be/ZDa-Z5JzLYM
  OOP 2 - https://youtu.be/BJ-VvGyQxho
  OOP 3 - https://youtu.be/BJ-VvGyQxho
  OOP 4 - https://youtu.be/RSl87lqOXDE
  OOP 5 - https://youtu.be/3ohzBxoFHAY
  OOP 6 - https://youtu.be/jCzT9XFZ5bw

  Public / private attributes proposed using property decorator
  at https://www.python-course.eu/python3_properties.php
  
"""

class Employee:
    num_of_emps = 0     # Class variable - equ static
    raise_amount = 1.04 # Class variable - equ static

    # Constructor    
    def __init__(self, f, l, p):
        self.first = f              # Instance
        self.last = l               # Instance
        self.pay = p                # Instance
        Employee.num_of_emps += 1   # Class var

    #  Instance Methods
    #  self is equ of this (instance)
    #  self must appear as first parm of class method
    #  self is passed implicitly as first parm when calling methid
    def apply_raise(self):
        self.pay = int(self.pay * Employee.raise_amount)

    #  Class Methods - to make, decorate with @classmethod. This
    #  Tells Python the first argument is the class, not instance
    @classmethod
    def set_raise_amt(cls,amount):  # Can't use "class" keyword in first pos
        cls.raise_amt = amount

    #  Overloading constructors (and other methods)
    @classmethod
    def from_string(cls, emp_str):
        first, last, pay = emp_str.split('-')
        return cls(first, last, int(pay))

    #  Static Methods - don't pass anything (cls or self)
    #  Use decorator. Tipoff whether a method should be static
    #  rather than class or instance, a static method won't reference
    #  Class or Instance variables.
    @staticmethod
    def is_workday(day) :
        if (day.weekday ==5) or (day.weekday == 6) :
            return False
        return True

    # getter, setter, deleter
    # Decorating with @property makes Python treat the "fullname"
    # function as if it was an instance variable. Makes function the equ of
    # a getter function. Note fullname relies onn other instance variables for state.
    @property
    def fullname(self):
        return '{} {}'.format(self.first, self.last)

    # Overloading fullname with decorators    
    # fullname.setter and fullname.deleter decorations tells python to treat
    # these functions as operators on the virtual instance var
    @fullname.setter
    def fullname(self, name):
       first, last = name.split(' ')
       self.first = first
       self.last = last

    @fullname.deleter
    def fullname(self):
       self.first = None
       self.last = None
       self.pay = None
       self.email = None

    @property
    def email(self):
        return '{}.{}@company.com'.format(self.first,self.last)

    # Emulating built-in Python behavior
    # Operator overloading
    # __ is called a 'dunder'
    # two dunder methods should almost always be implemented __repr__ and __str__
    # If __str__ is not implemented, __repr__ is called
    def __repr__(self): # Representation for other developers
        return "Employee('{}','{}','{}')".format(self.first, self.last, self.pay)

    def __str__(self):  # Representation for users
        return '{} - {}'.format(self.fullname, self.email)

      
    # Operator overloading
    # supports emp_1 + emp_2
    def __add__(self, other):
        return self.pay + other.pay

    # Supports concept of Employee - in this case just the length of the full name
    def __len__(self):
        return len(self.fullname())

## Comments on class:
##  1.  Equivalancy of calling from class vs calling from instance
##      (assuming class and method definitions are correct.)
##        emp_1.fullname()
##        Employee.fullname(emp_1)  #Same as previous line
##  2.  BAD:
##        self.pay = int(self.pay * self.raise_amount)
##      Danger, danger! Using instance instead of Class for raise amount
##      means that if someone directly sets raise_amount, e.g.
##        emp = Employee(myfirst, mylast, 50000)
##        emp.raise_amount = 1.10
##      then
##      python converts raise_amount into an INSTANCE ARIABLE! It will
##      no longer be shared at the class level.
##      GOOD: explicitly use the Class variable
##        self.pay = nt(self.pay * Employee.raise_amount)
##  3.  Subclasses walk up inheritance chain to resolve method call.
##      This is called method resolution order.
##        class Developer(Employee)
##          def __init__(self, first, last, pay, prog_lang)
##            super().__init__(first, last, pay)
##            self.lang = prog_lang
##      Subclass can call superclass __init__()
##      Can use name of superclass explicitly, but super() will
##      *always* follow method resolution order.
##      So Employee class __init__() will be called for Developer
##      Info is available by calling help(Developer):
##         Developer
##         Employee
##         built-in
##  4.  Python fxn isinstance() tells us if an object is an instance of a class
##        isinstance(dev_1, Developer)  # is true if dev_1 is type Developer
##        isinstance(dev_1, Employee)     # is true if dev_1 is type subclassed from Employee
##      Python issubclass() tells us if a class is a subclass of anotheer
##        issubclass(Developer, Employee)     # True if Developer is subclass of Employee


