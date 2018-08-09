"""
  Works in python 3.6.2
   Can be iniated at bash prompt in two different ways. This file must be in the '.'
   directory of the bash prompt.
     jhake$ python test_args.py                 # sys.argv[0] returns file name
     jhake$ python -m test_args 'arg1' 'arg2'   # sys.argv[0] returns full path/file name
   Can be iniated at python prompt in this way
     >>> import test_args
     >>> test_args.test_args(['foo', 'bar', 'fubar'])
   Note invocation in python is funamentally different - there is no sys.argv[] passed
   to main() or test_args, so we have to create an equivalent one.
"""

import sys

def main():
  print('Main executing')
  test_args(sys.argv)

def test_args(args):
  # if args[0] is not None :
    # print(args[0])
  for w in args:
    print(w)

if __name__ == "__main__":
    # execute only if run as a script
    main()


