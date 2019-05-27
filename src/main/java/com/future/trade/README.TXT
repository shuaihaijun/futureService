Java JDK must be of version >= 1.5


1. Start terminal server

start ..\bin\run_terminal_server.bat 


2. Edit / compile jfx examle

javac -classpath .;..\lib\jfx-2.8.5-P5.jar JFXExample.java JFXFactoryExample.java DemoAccount.java 


3. Run example

java -cp ..\lib\jfx-2.8.5-P5.jar;..\lib\log4j.jar;. JFXExample

    - OR - with "multi" arg to connect to two different MT4 brokers simultaneously:

java -cp ..\lib\jfx-2.8.5-P5.jar;..\lib\log4j.jar;. JFXExample multi


4. Run example to listen for MT4 jfx.ex4 EA tester

java -cp ..\lib\jfx-2.8.5-P5.jar;..\lib\log4j.jar;. JFXExample test
    - OR -
java -cp ..\lib\jfx-2.8.5-P5.jar;..\lib\log4j.jar;. JFXFactoryExample


5. Run example to listen for MT4 jfx.ex4 EA tester using UDP-based communication protocol

java -cp ..\lib\jfx-2.8.5-P5.jar;..\lib\log4j.jar;. -Djfx_use_udp=true JFXExample test
