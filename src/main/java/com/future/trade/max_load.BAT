@echo off

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

javac -classpath %CLASSPATH% JFXExample.java JFXFactoryExample.java JFXStreamerExample.java DemoAccount.java AlpariUKDemo100.java 

java -cp %CLASSPATH% -Djfx_activation_key=%JFX_ACTIVATION_KEY% JFXExample max
