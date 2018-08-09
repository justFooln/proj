from collections import namedtuple

CONST = namedtuple(
    typename = 'CONST',
    field_names=['C']
    )

"""
    Given the above namedtuple, if I say:
    from namedtuple import CONST
    Q = CONST(3)

##    To create a new CONST based on P why do I have to say:
    P = Q._replace(C=2)   # Works
##    instead of:
    P = Q._replace(3)

"""

Color = namedtuple('Color', ['red', 'green', 'blue'])
WHITE = Color(255, 255, 255)

