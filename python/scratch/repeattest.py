import repeat
from repeat import say_again

def main():
    print (say_again('Yay', False))      ## YayYayYay
    print (say_again('Woo Hoo', True))   ## Woo HooWoo HooWoo Hoo!!!


if __name__ == '__main__':
    main()
