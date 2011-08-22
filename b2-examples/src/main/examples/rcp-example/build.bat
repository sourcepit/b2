@echo off
echo.
echo ------------------------------------------------------------------------
echo Starting module build for '%~n1'
echo ------------------------------------------------------------------------
echo Directory    : %cd%
echo Command Line : %MAVEN_EXEC% %MAVEN_ARGS% -P buildProducts
echo.
call %MAVEN_EXEC% %MAVEN_ARGS% -P buildProducts