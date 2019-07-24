#!/bin/sh
# Calls git gc on all repositories
# you can disable git auto gc with "git config --global gc.auto 0" and run this script manually as needed

git gc

cd contrib
git gc

cd ../CIDR
git gc

cd ../community
git gc

cd android
git gc

cd tools-base
git gc
