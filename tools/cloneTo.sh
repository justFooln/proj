#!/bin/sh
# Creates a copy of IntelliJ IDEA repositories by cloning the local copies instead of fetching them via network
# Usage: ./cloneTo.sh <target directory>

set -e # Any command which returns non-zero exit code will cause this shell script to exit immediately
set -x # Activate debugging to show execution details: all commands will be printed before execution

# Absolute path to directory containing existing IntelliJ IDEA repo (and this script as well)
OLD_REPO="$(cd "`dirname "$0"`"; pwd)"

git clone "$OLD_REPO" "$1"

cd "$1"
# Absolute path to directory containing cloned IntelliJ IDEA repo
NEW_REPO="$(pwd)"

git clone "$OLD_REPO/community" "$NEW_REPO/community"
git clone "$OLD_REPO/contrib" "$NEW_REPO/contrib"
git clone "$OLD_REPO/community/android" "$NEW_REPO/community/android"
git clone "$OLD_REPO/community/android/tools-base" "$NEW_REPO/community/android/tools-base"
git clone "$OLD_REPO/CIDR" "$NEW_REPO/CIDR"

git remote set-url origin git@git.labs.intellij.net:idea/ultimate
cd "$NEW_REPO/community"
git remote set-url origin git@git.labs.intellij.net:idea/community
cd "$NEW_REPO/contrib"
git remote set-url origin git@git.labs.intellij.net:idea/contrib
cd "$NEW_REPO/community/android"
git remote set-url origin git@git.labs.intellij.net:idea/android
cd "$NEW_REPO/community/android/tools-base"
git remote set-url origin git@git.labs.intellij.net:idea/adt-tools-base.git
cd "$NEW_REPO/CIDR"
git remote set-url origin git@git.labs.intellij.net:idea/cidr

cp "$OLD_REPO/.idea/workspace.xml" "$NEW_REPO/.idea/"
