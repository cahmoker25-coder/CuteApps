#!/usr/sh
dirname_prg=`dirname "$0"`
app_home=`cd "$dirname_prg" && pwd`
APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

if [ -n "$MAX_FD" ] ; then
  MAX_FD_LIMIT="$MAX_FD"
fi

case "`uname`" in
    CYGWIN* | MINGW* | MSYS* )
        app_home=`cygpath --mixed "$app_home"`
        ;;
esac

if [ -n "$JAVA_HOME" ] ; then
    JAVACMD="$JAVA_HOME/bin/java"
else
    JAVACMD="java"
fi

CLASSPATH=$GRADLE_HOME/gradle/wrapper/gradle-wrapper.jar
exec "$JAVACMD" $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS "-Dorg.gradle.appname=$APP_BASE_NAME" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
