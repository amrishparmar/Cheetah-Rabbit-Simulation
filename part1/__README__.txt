====== README =======

The program is developed in Java 8 using the Processing 3 libraries.

Please double-click on the Part1.JAR file to run the program.
If you wish to launch it from the commmand line, you can use the following command:

java -jar Part1.jar

====== Compilation ======
To compile from source you can run the following command (Mac/Linux):
	
javac -cp part1/core.jar $(find * -name \*.java)


====== Running ======
To then run from compiled files, run the following command (Mac/Linux):

java -cp .:part1/core.jar part1.core.Main

