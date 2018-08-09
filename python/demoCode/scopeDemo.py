

def scope_demo():

    # definition of variable is local to do_local(), and so doesn't survive
    # when method goes out of scope. (This draws a weak warning that the local
    # variable is not used.)
    def do_local():
        spam = "local spam"

    # definition of variable is specifically not local to do_nonlocal(), so is defined
    # in scope_demo() namespace. Thus value change survives when do_nonlocal() goes
    # out of scope.
    def do_nonlocal():
        nonlocal spam
        spam = "nonlocal spam"

    # definition of variable is specifically declared to exist outside of scope_demo(), at the
    # module level. Thus value is only seen by isolated statement (below) outside the
    # scope of scope_demo().
    def do_global():
        global spam
        spam = "global spam"

    spam = "test spam"
    do_local()
    print("After local assignment:", spam)
    do_nonlocal()
    print("After nonlocal assignment:", spam)
    do_global()
    print("After global assignment:", spam)


scope_demo()
print("In global scope:", spam)
