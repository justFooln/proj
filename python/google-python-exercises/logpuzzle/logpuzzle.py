#!/usr/bin/python
# Copyright 2010 Google Inc.
# Licensed under the Apache License, Version 2.0
# http://www.apache.org/licenses/LICENSE-2.0

# Google's Python Class
# http://code.google.com/edu/languages/google-python-class/

import os
import re
import sys
import urllib.request
import ssl
import shutil

"""Logpuzzle exercise
Given an apache logfile, find the puzzle urls and download the images.

Here's what a puzzle url looks like:
10.254.254.28 - - [06/Aug/2007:00:13:48 -0700] "GET /~foo/puzzle-bar-aaab.jpg HTTP/1.0" 302 528 "-" "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.6) Gecko/20070725 Firefox/2.0.0.6"
"""


def read_urls(filename, server_name='http://code.google.com/'):
  """Returns a list of the puzzle urls from the given log file,
  extracting the hostname from the filename itself.
  Screens out duplicate urls and returns the urls sorted into
  increasing order.
  parm: filename    local filename to be parsed for image paths
        server_name URL of server where images are located -  note trailing / must be supplied"""
  # Construct unique URLs from file as -  http://code.google.com/<url from file>
  animal_list = []
  ordered_list = []
  src_file = open(filename, 'rU')
  for line in src_file :
    animal_path = re.search( 'GET\s+/(.+jpg)', line )
    if animal_path is not None :
      if animal_path.group(1) not in animal_list :
        animal_list.append( animal_path.group(1) )
  ordered_list = sorted(animal_list,key=sort_img_name)
  # Used in in range loop to operate on ordered_list rather than shallow copy, e.g. for path in ordered_list
  for i in range(0, len(ordered_list), 1) :
    ordered_list[i] = server_name + ordered_list[i]
  return ordered_list


def sort_img_name(img_url):
  rank = ''
  parse_img = re.search( '/(\w-\w+).jpg', img_url )
  if parse_img is not None :
    rank = parse_img.group(1)
  return rank

  

def download_images(img_urls, dest_dir):
  """Given the urls already in the correct order, downloads
  each image into the given directory.
  Gives the images local filenames img0, img1, and so on.
  Creates an index.html in the directory
  with an img tag to show each local image file.
  Creates the directory if necessary.
  """
  if len(img_urls) > 0 :
    if not os.path.exists(dest_dir):
      os.mkdir(dest_dir)
    # save each images file name
    image_names = []
    # Iterate over each image url, downloading the image to a local file
    img_ctr = 0
    for url in img_urls :
      file_name = 'img' + str(img_ctr) + '.jpg'
      image_names.append(file_name)
      full_name = dest_dir + '/' + file_name
      print('Writing file: %s from %s' % (full_name, url) )
      # When calling the SSLContext constructor directly, CERT_NONE is the default.
      # Since it does not authenticate the other peer it can be insecure
      # Beyond the scope of this exercise (emoji holding my nose)
      unsecure_context = ssl.SSLContext(ssl.PROTOCOL_TLSv1)
      with urllib.request.urlopen(url, context=unsecure_context) as response, open(full_name, 'wb') as out_file:
        shutil.copyfileobj(response, out_file)
      img_ctr += 1
  return image_names


def write_html(img_name, dest_dir):
  if (len(img_name)>0) and os.path.exists(dest_dir) :
    img_string = ''
    # Build line <img src="/path-to-image-file/img0">...
    for img in img_name :
      img_string += '<img src="' + img + '">'
    html_file = open(dest_dir + '/' + 'puzzle.html', 'w')
    html_file.write('<verbatim>\n')
    html_file.write('<html>\n')
    html_file.write('<body>\n')
    html_file.write(img_string + '\n')
    html_file.write('</body>\n')
    html_file.write('</html>\n')
    html_file.close()


def main():
  args = sys.argv[1:]

  if not args:
    # should be run from logpuzzle directory using:
    #   $ python -m logpuzzle '--todir' './test' 'animal_code.google.com'
    print( 'usage: [--todir dir] logfile ' )
    sys.exit(1)

  todir = ''
  if args[0] == '--todir':
    todir = args[1]
    del args[0:2]
    logfile_name = args[0]
    img_urls = read_urls(logfile_name,'http://code.google.com/')
    img_names = download_images(img_urls, todir)
    write_html(img_names, todir)

main()
