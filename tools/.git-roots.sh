#!/bin/bash
ROOTS=( \
  "/" \
  "/android" \
  "/android/tools-base" \
)
export ROOTS

function all_tags_remote() {
  repo=$(case "$1" in
    ("/") echo "community.git" ;;
    ("/android") echo "android.git" ;;
    ("/android/tools-base") echo "adt-tools-base.git" ;;
    (*) exit 1 ;;
  esac)
  echo "git://git.labs.intellij.net/idea-all-tags/$repo"
}


