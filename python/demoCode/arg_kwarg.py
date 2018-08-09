# https://youtu.be/CfI4cR66jQY


# Pass by direct argument
# Calling function with explicit argument values allow changing order
# BUT all non-default or unspecified argument values must come first.
    newx, newy = move(x, y, distance=3, direction='right')    # OK
#   newx, newy = move(x, distance=3, y, direction='right')    NOT OK
# Giving an argument a default value makes it a keyword
# Keywords can be omitted in calling if default value is desired
    newx, newy = move(x, y, direction='right')    # OK

def move( x, y, direction, distance=1 )
    if direction == 'up':
        y += distance
    elif direction == 'down':
        y -= distance
    elif direction = 'left' :
        x -= distance
    elif direction = 'right' :
        x += distance
    else:
        raise Exception('Invalid direction')
    return x, y


# Better Way *arg -- Argument Lists
# *mult_dirs is a [] of direction strings passed as [] or additional arguments
# e.g. call would be (note that call uses default distance):
    newx,newy = move_more(x, y, 'up', 'left') # 'up,'left' become ['up','left]

def move_more( x, y, *mult_dirs)
    for direct in mult_dirs:
        x,y = move(x, y, direct)
    return x,y


# Best Way *arg & **kwarg -- Argument Lists & Arguement dictionaries
# *dirs is a [] of direction strings
# **dist is a {} (dict) of keywords => {'distance' : 5}
# Convenient to pack many arguments into a dict for **kwarg
# Note the pass through of **dist - the key must match what move fxn is expecting.
# e.g. call would be:
    newx, newy = move_further(x, y, 'up', 'left', distance=5)

def move_again( x, y, *mult_dirs, **kwargs)      # **name doesn't matter
    for direct in mult_dirs:
        x,y = move(x, y, direct, **kwargs)
    return x,y

def move_further( x, y, *mult_dirs, **dist)
    for direct in mult_dirs:
        x,y = move_again(x, y, direct, **dist)
    return x,y

