:: Calls git gc on all repositories
:: you can disable git auto gc with "git config --global gc.auto 0" and run this script manually as needed

call git gc

cd contrib
call git gc

cd ../CIDR
call git gc

cd ../community
call git gc

cd android
call git gc

cd tools-base
call git gc
