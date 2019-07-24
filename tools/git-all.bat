@echo off
REM Executes passed command for each root in repository

setlocal

SET IDE_ROOT=%~dp0

REM For some reason ANSI sequences are not working in FOR loop

SET D=%IDE_ROOT%
echo [32m[git %*][0m [94m%D%[0m
cd "%D%" & git %*
echo [31m[%ERRORLEVEL%][0m [94m%D%[0m

SET D=%IDE_ROOT%contrib
echo [32m[git %*][0m [94m%D%[0m
cd "%D%" & git %*
echo [31m[%ERRORLEVEL%][0m [94m%D%[0m

SET D=%IDE_ROOT%community
echo [32m[git %*][0m [94m%D%[0m
cd "%D%" & git %*
echo [31m[%ERRORLEVEL%][0m [94m%D%[0m

SET D=%IDE_ROOT%community\android
echo [32m[git %*][0m [94m%D%[0m
cd "%D%" & git %*
echo [31m[%ERRORLEVEL%][0m [94m%D%[0m

SET D=%IDE_ROOT%community\android\tools-base
echo [32m[git %*][0m [94m%D%[0m
cd "%D%" & git %*
echo [31m[%ERRORLEVEL%][0m [94m%D%[0m

SET D=%IDE_ROOT%CIDR
echo [32m[git %*][0m [94m%D%[0m
cd "%D%" & git %*
echo [31m[%ERRORLEVEL%][0m [94m%D%[0m
