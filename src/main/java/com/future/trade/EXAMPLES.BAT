@echo off
REM type README.TXT

:: Set JAVA_HOME
set KeyName=HKEY_LOCAL_MACHINE\SOFTWARE\JavaSoft\Java Development Kit
set Cmd=reg query "%KeyName%" /s
for /f "tokens=2*" %%i in ('%Cmd% ^| find "JavaHome"') do set JAVA_HOME=%%j
set PATH=%PATH%;%JAVA_HOME%\bin

set JFX_HOME=%~dp0
cd "%JFX_HOME%"

set CLASSPATH="%JFX_HOME%..\lib\jfx-2.8.5-P5.jar"
set CLASSPATH=%CLASSPATH%;"%JFX_HOME%..\bin\nj4x-ts-2.8.5-P5.exe"
set CLASSPATH=%CLASSPATH%;"%JFX_HOME%..\examples"

call which.bat javac.exe

echo _
echo _
echo _
echo INFO: The following javac compiler has been detected: %which%
echo INFO: Press ENTER to continue using it (Control-C, Y: to abort)
echo _
pause

echo _
echo ---------------------------------------------------------------
echo INFO: Start terminal server
echo _______________________________________________________________
pause

start %JFX_HOME%..\bin\run_terminal_server.bat 


echo _
echo ---------------------------------------------------------------
echo INFO: Compile some examples
echo INFO: CLASSPATH=%CLASSPATH%
echo _______________________________________________________________
pause

javac -classpath %CLASSPATH% JFXExample.java JFXFactoryExample.java JFXStreamerExample.java DemoAccount.java AlpariUKDemo100.java 

echo _
echo ---------------------------------------------------------------
echo INFO: Run JFXStreamerExample
echo _______________________________________________________________
pause

java -cp %CLASSPATH% -Djfx_activation_key=%JFX_ACTIVATION_KEY% JFXStreamerExample

echo _
echo ---------------------------------------------------------------
echo INFO: Run JFXExample
echo INFO: ** Connection time should be significantly reduced 
echo _______________________________________________________________
pause

java -cp %CLASSPATH% -Djfx_activation_key=%JFX_ACTIVATION_KEY% JFXExample

echo _
echo ---------------------------------------------------------------
echo INFO: Wait for manual MT4 JFX EA connection (JFXExample)
echo _______________________________________________________________
pause

java -cp %CLASSPATH% -Djfx_activation_key=%JFX_ACTIVATION_KEY% JFXExample test
