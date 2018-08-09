"""
    Demonstrates using type hints and static type checking with mypy, a Python
    lint utility installed using pip.
    Python ignores these hints at runtime; Python is always a dynamic type
    language.
    At 11:00 in video shows extension to list, dict
    https://youtu.be/rytP_vIjzeE

"""

# Note these are NOT the same as the python types
from typing inport Callable, List, Dict, Any, Union

# This function expects an int OR float, and returns a float
def factorial(i:Union[int,float], -> int:
    """
        e.g.    3!=3x2x1
                1!=1
                0!=1
    """

    # mypy will never flag None as a type violation so we have to
    # explicitly handle it in the code.
    if i is None:
        return None

    # Because the Union[int, float] specified for i, we have to
    # explicitly cast i to int
    i = int(i)

    if i < 0:
        return None
    if i == 0:
        return 1
    # if none of the special cases, use recursion to calculate
    # the next number in the sequence.
    return i*factorial(i-1)



######################## Some example statements

#1
print(factorial(None))                      # included at bottom of typeHintsStaticTypeChk.py
python -m mypy typeHintsStaticTypeChk.py    # run at bash prompt produces nothing (means no static type violations)

#2
print(factorial(2.01))                      # included at bottom of typeHintsStaticTypeChk.py
python -m mypy typeHintsStaticTypeChk.py    # run at bash prompt produces nothing (means no static type violations)

#3
print(factorial('5'))                      # included at bottom of typeHintsStaticTypeChk.py
python -m mypy typeHintsStaticTypeChk.py    # run at bash prompt produces static type violation


              
    
              
