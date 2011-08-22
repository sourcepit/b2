@echo off

if "%2"=="reactor" goto performReactorBuild
if "%2"=="single" goto performReactorBuild
goto printOptions

:performSingleBuilds
cd rcp-help
echo.
echo.
echo.
echo Starting module build for 'rcp-help'
echo.
echo Directory    : %cd%
echo Command Line : %MAVEN_EXEC% %MAVEN_ARGS%
echo.
call %MAVEN_EXEC% %MAVEN_ARGS%

cd ../rcp-ui
echo.
echo.
echo.
echo Starting module build for 'rcp-ui'
echo.
echo Directory    : %cd%
echo Command Line : %MAVEN_EXEC% %MAVEN_ARGS%
echo.
call %MAVEN_EXEC% %MAVEN_ARGS%

cd ../rcp
echo.
echo.
echo.
echo Starting module build for 'rcp'
echo.
echo Directory    : %cd%
echo Command Line : %MAVEN_EXEC% %MAVEN_ARGS% -P buildProducts
echo.
call %MAVEN_EXEC% %MAVEN_ARGS% -P buildProducts
goto end

:performReactorBuild
echo.
echo.
echo.
echo Starting reactor build for 'rcp, rcp-help, rcp-ui'
echo.
echo Directory    : %cd%
echo Command Line : %MAVEN_EXEC% %MAVEN_ARGS% -P buildProducts
echo.
call %MAVEN_EXEC% %MAVEN_ARGS% -P buildProducts
goto end

:printOptions
echo.
echo You can choose either if you want to build the modules in this example all together in a reactor build or each on its own, sharing dependencies through generated update sites.
echo.
echo Re-run this example with "run.bat <example> reactor" or "run.bat <example> single"
echo.
:end