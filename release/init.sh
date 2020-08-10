#!/bin/sh
JAVA_OPTS="${JAVA_JVM_ARGS} $(appenv)"
export JAVA_OPTS
java $JAVA_OPTS -jar /app/restate.jar
