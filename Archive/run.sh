#!/bin/bash
export CLASSPATH=`find ./lib -name "*.jar" | tr '\n' ':'`
export MAINCLASS=main
java -cp ${CLASSPATH}:classes $MAINCLASS
