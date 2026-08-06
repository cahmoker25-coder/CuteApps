@ECHO off
setlocal
set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"
set JAVACMD=
if "%JAVA_HOME%" == "" goto gnu-env
set JAVACMD=%JAVA_HOME%\bin\java.exe
if exist "%JAVACMD%" goto gnu-env
:gnu-env
if "%JAVACMD%" == "" set JAVACMD=java
"%JAVACMD%" %DEFAULT_JVM_OPTS% $JAVA_OPTS $GRADLE_OPTS "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
endlocal
