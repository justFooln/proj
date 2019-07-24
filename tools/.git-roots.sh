#!/bin/bash
ROOTS=( \
  "/" \
  "/contrib" \
  "/community" \
  "/community/android" \
  "/community/android/tools-base" \
  "/CIDR" \
)
export ROOTS

function all_tags_remote() {
  repo=$(case "$1" in
    ("/") echo "ultimate.git" ;;
    ("/contrib") echo "contrib.git" ;;
    ("/community") echo "community.git" ;;
    ("/community/android") echo "android.git" ;;
    ("/community/android/tools-base") echo "adt-tools-base.git" ;;
    ("/CIDR") echo "cidr.git" ;;
    (*) exit 1 ;;
  esac)
  echo "git://git.labs.intellij.net/idea-all-tags/$repo"
}