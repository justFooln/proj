#!/usr/bin/python
# Copyright 2010 Google Inc.
# Licensed under the Apache License, Version 2.0
# http://www.apache.org/licenses/LICENSE-2.0

# Google's Python Class
# http://code.google.com/edu/languages/google-python-class/

import sys
import re
import os
import shutil
import subprocess

_verbose = False

"""
Copy Special exercise
   Operates on filenames that match the description '\w+__\w+__.\w{3}'
   Invocation examples:
      $ python -m copyspecial --todir destDir srcDir            # Copies srcDir to destDir
      $ python -m copyspecial --tozip srcDir destFile           # Zips contents of srcDir destFile
      $ python -m copyspecial srcDir                            # Lists srcDir
"""

def get_special_paths(dir) :
  if _verbose is True :
    print('get_special_paths, parms are: ', dir )
  filenames = os.listdir(dir)
  if _verbose is True :
    print( 'filenames: ', filenames )
  special_names = []*0
  for name in filenames :
    special = re.search('\w+__\w+__.\w{3}', name)
    if special is not None :
      short_path = os.path.join(dir, special.group())
      abby_path = os.path.abspath(short_path)
      if _verbose is True :
        print (special.group(), '  ', short_path)
        print(abby_path)
      special_names.append(abby_path)
  return special_names


def to_dir(src_files, target_dir):
  if _verbose is True :
    print( 'in target_dir' , src_files, target_dir)
  if len(src_files) > 0 :
    if os.path.isdir(target_dir) is not True :
      print ('creating target_dir: ', target_dir)
      os.mkdir(target_dir)
    for each_file in src_files :
      shutil.copy2(each_file, target_dir)



def to_zip(src_files, target_zip):
  if _verbose is True :
    print( 'to_zip: ', src_files, target_zip )
  if len(src_files) > 0 :
    cmd_text = ["zip","-j", target_zip]
    for each_file in src_files :
      cmd_text.append(each_file)
    print('about to execute cmd: ', cmd_text)
    # subprocess.run(["zip","-j", "test.zip", "/Users/jhake/Documents/source/python/google-python-exercises/copyspecial/zz__something__.jpg"])  
    subprocess.run(cmd_text)  


def main():
  # This basic command line argument parsing code is provided.
  # Add code to call your functions below.

  # Make a list of command line arguments, omitting the [0] element
  # which is the script itself.
  if _verbose is True :
    print('Sys passed in: ', sys.argv)
  args = sys.argv[1:]
  if not args:
    print( "usage: [--todir destDir] | [--tozip zipFileName] ;  [SrcDir ...]" )
    sys.exit(1)

  # todir and tozip are either set from command line
  # or left as the empty string.
  # The args array is left just containing the dirs.
  todir_dest = ''
  if args[0] == '--todir':
  # Capture the cmd and then remove the todir command and destination directory
    if _verbose is True :
      print( 'Found --todir cmd, args are: ', args[0:] )
    todir_dest = os.path.abspath(args[1])
    del args[0:2]

  tozip_dest = ''
  if args[0] == '--tozip':
    # Capture the cmd and then remove the cmd and directory
    if _verbose is True :
      print( 'Found --tozip cmd, args are: ', args[0:])
    tozip_dest = args[1]
    del args[0:2]

  if len(args) == 0:
    print ("error: must specify one or more dirs")
    sys.exit(1)
  else:
    src_dir = os.path.abspath(args[0])

  # +++your code here+++
  # Call your functions
  if _verbose is True :
    print('args are ', args)
  path_names = get_special_paths(src_dir)

  if todir_dest is not '' :
    to_dir(path_names, todir_dest)
  elif tozip_dest is not '' :
    to_zip(path_names, tozip_dest)
  else:
    print(path_names)



main()
