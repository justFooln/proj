# From https://youtu.be/VBokjWj_cEA - Sebastion Mathot


#1 Use enumerate instead of a counter variable
    cities = [seattle, sacramento, portland, lodi]
    for i, city in enumerate(cities):
        print(i,cities)


#2 Use zip instead of using range in a for loop
    x_list = [10, 20, 30 ]
    y_list = [20, 40, 60 ]
    for x,y in zip(x_list, y_list):
        print(x, y)


#3 Use tuple unpacking instead of tmp variables for a swap
    x = 10
    y = -10
    x, y = y, x


#4 Use dict.get() when searching dictionaries
#   Allows passing a default value if index not found
    ages =  {
            'Mary' : 31,
            'Jonathan' : 28
            }
    age = ages.get('Dick', 'Not Found')


#5 for loops have else clause
    needle = 'd'
    haystack = ['a', 'b' 'c']
    for letter in haystack:
        if needle == letter:
            print('Found')
            break
    else:  # no break ocurred
        print('Not found')


#6 A file object supports iteration.
#  Using 'with' context eliminates need for separate
#  f=open and fclose stmts        
        # f = open('myfile.txt'):
        with open('myfile.txt') as f:
            for line in f:
                print(line)
            # fclose()  Not needed using with context
        
#7 try - except statments have additional handlers
    print('Converting')
    try:
        print(int('1'))
    except:    # Called if code under try failed
        print('Conversion Failed')
    else:      # Called if code under try succeeded
        print('Conversion Succeeded')
    finally:   # always called before error
               # propogates, good for cleanup
        print('Done')


# From https://youtu.be/SNTZpy0oDB8 - Sebastion Mathot

#8 Comparing tuples and lists, instead of looping use in-line
#  if-else statements for assignment. Doesn't crash even if tuples/lists
#  are different length - BUT unequal length tuples/lists are always unequal.
#  NOTE that longer tuples/sequences are considered greater than shorter
#  ones, ie (3,5,2,0) > (3,5,2)
    latest_python = (3,5,2)
    my_python = (3,5,2)
    msg = 'update available' if latest_python > my_python else 'up to date'


#9  Extended Unpacking in Python3
    cities = ['G', 'Ma', 'B', 'Mu']
    # Traditional way
    first = cities[0]
    last = cities[-1]
    # Python 3 way of extended unpacking
    first, *rest, last = cities
    # first now is 'G', last now is 'Mu', rest is [Ma,'B']


#10 Dictionary comprehension - incorporate interative loop into instantiation
#   of dict from two lists
#   Can make code difficult to read, so use judiciously
    import string
    cities = ['g', 'ma', 'b', 'mu']
    populations = [1, 2, 3, 4]
    # d = {}
    # for c, p in zip(cities,populations): d[string.capwords(c)] = p
    d = { string.capwords(c) : p for c, p in zip (cities,populations) }


#11 Dictionaries to not preserve order as operations are performed on them.
#   To impose an order, use OrderedDic fxn.
#   Note, this example could be compressed into comprehension, but code gets
#   difficult to read.
    import string, collections
    cities = ['g', 'ma', 'b', 'mu']
    populations = [1, 2, 3, 4]
    d = collections.OrderedDic()        #Instantiate empty ordered dict
    # Iterate over cities, adding capitalized cities to d & assoc with p value.
    for c,p in zip(cities, populations): d[string.capwords(c)] = p


#12 Default dict - improved way of handling default values - see tips 4 & 13
#   This approach uses lambda fxns - defining a function with default return
#   value on a single line.
    import collections
    butt = {
        'B'     : 2,
        'C'     : 11,
        'G'     : 1,
        'Bl'    : 3
        }
    d = collections.defaultdict(lambda: 0)      #Instantiate a dict with default 0 return value
    d.update(butt)                              #Add data dict
    p_o = d['P']                                #Lookup an undefined key, value is 0


#13 Common, but specialized problem is getting values for keys that are not
#   defined in a dict. Use collections.Counter() type
    import collections
    butt = {
        'B'     : 2,
        'C'     : 11,
        'G'     : 1,
        'Bl'    : 3
        }
    b_c = collections.Counter(butt)             #Create type counter from butt
    p_o = b_c['P']                              #Returns 0 by default
    
