@echo off
set MAVEN_OPTS=%MAVEN_OPTS% -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000
call run.bat %*