@echo off
title Abendigo Controller

set bat="./build/install/abendigo-controller/bin/abendigo-controller.bat"

:loop
if exist %bat% (
    call %bat%
    pause
) else (
    call build.bat
    cls
    title Abendigo Controller
    goto loop
)