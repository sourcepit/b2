@echo off

set BASE=%~dp0
set M2_HOME=%BASE%apache-maven-3.0.3
set MAVEN_OPTS=-Duser.home=%BASE% %MAVEN_OPTS%

echo.
echo ------------------------------------------------------------------------
echo Setting build environment
echo ------------------------------------------------------------------------
echo User Home Directory    : %BASE%
echo Maven Home Directory   : %M2_HOME%
echo Maven Opts:            : %MAVEN_OPTS%

SET MAVEN_EXEC="%M2_HOME%\bin\mvn.bat"
SET MAVEN_ARGS=-e clean verify

cd %1

if exist "build.bat" goto customBuild

echo.
echo ------------------------------------------------------------------------
echo Starting module build for '%~n1'
echo ------------------------------------------------------------------------
echo Directory    : %cd%
echo Command Line : %MAVEN_EXEC% %MAVEN_ARGS%
echo.
call %MAVEN_EXEC% %MAVEN_ARGS%
goto end

:customBuild
call build.bat %*
goto end

:end
cd %BASE%