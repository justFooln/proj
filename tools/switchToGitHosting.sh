#!/bin/sh

set -x

(                                   git remote set-url origin ssh://git@git-hosting.labs.intellij.net/idea-ultimate.git               )
(cd CIDR                         && git remote set-url origin ssh://git@git-hosting.labs.intellij.net/idea-cidr.git                   )
(cd contrib                      && git remote set-url origin ssh://git@git-hosting.labs.intellij.net/idea-contrib.git                )
(cd community                    && git remote set-url origin ssh://git@git-hosting.labs.intellij.net/idea-community.git              )
(cd community/android            && git remote set-url origin ssh://git@git-hosting.labs.intellij.net/idea-community-android.git      )
(cd community/android/tools-base && git remote set-url origin ssh://git@git-hosting.labs.intellij.net/idea-community-android-tools.git)