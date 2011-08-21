@echo off

set BASE=%~dp0
set M2_HOME=%BASE%apache-maven-3.0.3
set MAVEN_OPTS=-Duser.home=%BASE% %MAVEN_OPTS%

echo User Home Directory    : %BASE%
echo Maven Home Directory   : %M2_HOME%
echo Maven Opts:            : %MAVEN_OPTS%

SET MAVEN_EXEC="%M2_HOME%\bin\mvn.bat"
SET MAVEN_ARGS=-e clean verify

echo Starting build for %1
cd %1
call build.bat
cd %BASE%