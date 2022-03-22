set SRC=src
set LIB=lib

set JUNIT=junit-platform-console-standalone-1.6.0.jar
set JAVA_FX=javafx-sdk-18/lib
set JAVA_FX_MODULES=javafx.controls

rmdir -r build
mkdir build


javac -d ./build %SRC%/IJAHomework1Test.java -classpath %SRC%;%LIB%/%JUNIT%

java -cp build;%LIB%/%JUNIT% org.junit.runner.JUnitCore IJAHomework1Test

