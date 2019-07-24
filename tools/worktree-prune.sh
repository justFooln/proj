#!/bin/sh

set -x
echo $1
                 git worktree prune
cd CIDR       && git worktree prune && cd -
cd contrib    && git worktree prune && cd -
cd community  && git worktree prune 
cd android    && git worktree prune 
cd tools-base && git worktree prune