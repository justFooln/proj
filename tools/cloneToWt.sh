#!/bin/bash
# Creates new worktrees for all IntelliJ IDEA repositories pointing to a new release branch.
# Usage: ./cloneToWt.sh <target directory> <branch>
# Restrictions:
#   There should be a local branch <branch>,
#   or a remote branch origin/<branch> from which a new local branch will be created by this script.
#   You must use Git 2.6.0 or later.


if [[ -z "$1" || -z "$2" ]] ; then
  echo "
  Usage:   ./cloneToWt.sh <target directory> <branch>
  Example: ./cloneToWt.sh ~/idea-143 143"
  exit 1
fi

NEW_REPO="$1"
BRANCH="$2"

if [[ "$BRANCH" == origin/* ]]; then
  BRANCH="${BRANCH/origin\//}"
fi

# Absolute path to directory containing existing IntelliJ IDEA repo (and this script as well)
OLD_REPO="$(cd "`dirname "$0"`"; pwd)"
ROOTS=("/" "/contrib" "/community" "/community/android" "/community/android/tools-base" "/CIDR")

if [ -d "$NEW_REPO" ]; then
  echo "Directory '$NEW_REPO' already exists"
  exit 2
fi


for ROOT in ${ROOTS[@]}; do
  g="git --git-dir=${OLD_REPO}${ROOT}/.git"
  exists=`$g worktree list | grep -F "[$BRANCH]"`
  if [ -n "$exists" ]; then
    echo "Branch '$BRANCH' is already checked out in some other worktree"
    exit 3
  fi
  unset exists g
done


set -e # Any command which returns non-zero exit code will cause this shell script to exit immediately


for ROOT in ${ROOTS[@]}; do
  echo
  g="git --git-dir=${OLD_REPO}${ROOT}/.git"
  exists=`$g branch "$BRANCH" --list`
  if [ -n "$exists" ]; then
    echo Repo ${ROOT}  -  checkout existent $BRANCH
    echo
    $g --work-tree="${OLD_REPO}${ROOT}" worktree add "${NEW_REPO}${ROOT}" "$BRANCH"
  else
    echo Repo ${ROOT}  -  checkout new origin/$BRANCH
    echo
    $g --work-tree="${OLD_REPO}${ROOT}" worktree add -b $BRANCH "${NEW_REPO}${ROOT}" origin/${BRANCH}
  fi
  unset exists g
done

cp -a "$OLD_REPO/.idea/workspace.xml" "$NEW_REPO/.idea/"

echo
echo OK.
