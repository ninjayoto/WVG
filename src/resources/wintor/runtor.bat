@echo off
GOTO STOP

:STOP
@echo off
taskkill /F /IM tor.exe

:MAIN
@echo off
cd %~dp0
start "" "%~dp0tor.exe"
exit
