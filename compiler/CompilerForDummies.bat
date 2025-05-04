@echo off
setlocal enabledelayedexpansion
call :printArt
goto :continue_execution

:printArt
for /f "delims=" %%A in ('findstr /r "^::ART:" %~f0') do (
    set "line=%%A"
    echo !line:~6!
)
goto :eof
::ART: .
::ART: .
::ART:         8888ba.88ba                                         oo                                    
::ART:         88  `8b  `8b                                                                              
::ART:         88   88   88 .d8888b. dP   .dP .d8888b. 88d888b.    dP .d8888b.                           
::ART:         88   88   88 88'  `88 88   d8' 88ooood8 88'  `88    88 Y8ooooo.                           
::ART:         88   88   88 88.  .88 88 .88'  88.  ... 88    88    88       88                           
::ART:         dP   dP   dP `88888P8 8888P'   `88888P' dP    dP    dP `88888P'                           
::ART:       .                                                                                           
::ART:       .                                                                                           
::ART: .8888b                                                   dP       dP oo                            
::ART: 88   "                                                   88       88                               
::ART: 88aaa  .d8888b. 88d888b.    dP  dP  dP .d8888b. .d8888b. 88  .dP  88 dP 88d888b. .d8888b. .d8888b. 
::ART: 88     88'  `88 88'  `88    88  88  88 88ooood8 88'  `88 88888"   88 88 88'  `88 88'  `88 Y8ooooo. 
::ART: 88     88.  .88 88          88.88b.88' 88.  ... 88.  .88 88  `8b. 88 88 88    88 88.  .88       88 
::ART: dP     `88888P' dP          8888P Y8P  `88888P' `88888P8 dP   `YP dP dP dP    dP `8888P88 `88888P' 
::ART:                                                                                      .88          
::ART:                                                                                  d8888

:continue_execution

echo.
echo Press any button to compile 'n' run...
pause >nul
cls

cd ..
echo Compiling and running the Java program from
echo %cd%
if exist compiler\sources.txt del compiler\sources.txt
dir /s /b src\*.java > compiler\sources.txt
javac -d compiler\target\classes @compiler\sources.txt
java -cp compiler\target\classes Main

pause
