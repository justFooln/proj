#!/usr/bin/python
# Copyright 2010 Google Inc.
# Licensed under the Apache License, Version 2.0
# http://www.apache.org/licenses/LICENSE-2.0

# Google's Python Class
# http://code.google.com/edu/languages/google-python-class/

import sys
import re

"""
  Given a file name for baby.html,
  <h3 align="center">Popularity in 1990</h3>
  <tr align="right"><td>1</td><td>Michael</td><td>Jessica</td>
  returns a list starting with the year string
  followed by the name-rank strings in alphabetical order.
  ['2006', 'Aaliyah 91', Aaron 57', 'Abagail 895', ' ...]
"""
def extract_data(filename, numeric=False):
  name_list = []
  ordered_list = []
  list_year = '0000'
  src_file = open(filename, 'rU')
  for line in src_file :
    year = re.search( 'Popularity\s+in\s+(\d{4})' , line)
    rank_name = re.search(  '<td>(\d+)</td><td>(\w+)</td><td>(\w+)</td>'  , line)
    if year is not None :
      list_year = year.group(1)
    if rank_name is not None :
      name_list.append( rank_name.group(2) + ' ' + rank_name.group(1) )
      name_list.append( rank_name.group(3) + ' ' + rank_name.group(1) )
  if numeric is True :
    ordered_list = sorted(name_list,key=sort_numeric)
  else :
    ordered_list = sorted(name_list)
  ordered_list.insert(0, list_year )
  return ordered_list


def sort_numeric(baby_string):
  rank = ''
  parse_baby = re.search( '\w+\s+(\d+)', baby_string)
  if parse_baby is not None :
    rank = parse_baby.group(1)
  return int(rank)


def baby_names(numeric=False):
  # For each filename, get the names, then either print the text output
  # or write it to a summary file
  popular_names = []
  survey_year = 1990
  while survey_year < 2009 :
    file_name = 'baby' + str(survey_year) + '.html' 
    popular_names = extract_data(file_name, numeric)
    print(popular_names[0:10])
    print('  ', popular_names[-10:])
    survey_year += 2
