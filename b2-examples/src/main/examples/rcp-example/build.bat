@echo off
SET MAVEN_ARGS=%MAVEN_ARGS% -P buildProducts
call %MAVEN_EXEC% %MAVEN_ARGS% 