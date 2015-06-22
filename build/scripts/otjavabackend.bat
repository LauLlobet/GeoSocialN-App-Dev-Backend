@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  otjavabackend startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and OTJAVABACKEND_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windowz variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\otjavabackend-1.0.jar;%APP_HOME%\lib\resources;%APP_HOME%\lib\slf4j-simple-1.6.0.jar;%APP_HOME%\lib\javassist-3.12.1.GA.jar;%APP_HOME%\lib\jersey-client-2.7.jar;%APP_HOME%\lib\jersey-media-json-jackson-2.7.jar;%APP_HOME%\lib\jersey-container-grizzly2-http-2.7.jar;%APP_HOME%\lib\jasypt-1.9.2.jar;%APP_HOME%\lib\postgresql-9.1-901-1.jdbc4.jar;%APP_HOME%\lib\geolatte-geom-1.0.jar;%APP_HOME%\lib\hibernate-spatial-5.0.0.CR1.jar;%APP_HOME%\lib\hibernate-entitymanager-5.0.0.CR1.jar;%APP_HOME%\lib\javaee-web-api-7.0.jar;%APP_HOME%\lib\jersey-common-2.7.jar;%APP_HOME%\lib\javax.ws.rs-api-2.0.jar;%APP_HOME%\lib\hk2-api-2.2.0.jar;%APP_HOME%\lib\javax.inject-2.2.0.jar;%APP_HOME%\lib\hk2-locator-2.2.0.jar;%APP_HOME%\lib\jackson-core-asl-1.9.13.jar;%APP_HOME%\lib\jackson-mapper-asl-1.9.13.jar;%APP_HOME%\lib\jackson-jaxrs-1.9.13.jar;%APP_HOME%\lib\jackson-xc-1.9.13.jar;%APP_HOME%\lib\grizzly-http-server-2.3.8.jar;%APP_HOME%\lib\jersey-server-2.7.jar;%APP_HOME%\lib\jts-1.13.jar;%APP_HOME%\lib\apfloat-1.8.2.jar;%APP_HOME%\lib\jboss-logging-3.3.0.Beta1.jar;%APP_HOME%\lib\jboss-logging-annotations-1.2.0.Final.jar;%APP_HOME%\lib\jboss-logging-processor-1.2.0.Final.jar;%APP_HOME%\lib\hibernate-core-5.0.0.CR1.jar;%APP_HOME%\lib\dom4j-1.6.1.jar;%APP_HOME%\lib\hibernate-commons-annotations-4.0.5.Final.jar;%APP_HOME%\lib\hibernate-jpa-2.1-api-1.0.0.Final.jar;%APP_HOME%\lib\geronimo-jta_1.1_spec-1.1.1.jar;%APP_HOME%\lib\javassist-3.18.1-GA.jar;%APP_HOME%\lib\javax.annotation-api-1.2.jar;%APP_HOME%\lib\jersey-guava-2.7.jar;%APP_HOME%\lib\osgi-resource-locator-1.0.1.jar;%APP_HOME%\lib\hk2-utils-2.2.0.jar;%APP_HOME%\lib\aopalliance-repackaged-2.2.0.jar;%APP_HOME%\lib\grizzly-http-2.3.8.jar;%APP_HOME%\lib\validation-api-1.1.0.Final.jar;%APP_HOME%\lib\jdeparser-1.0.0.Final.jar;%APP_HOME%\lib\antlr-2.7.7.jar;%APP_HOME%\lib\jandex-1.1.0.Final.jar;%APP_HOME%\lib\xml-apis-1.0.b2.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\grizzly-framework-2.3.8.jar;%APP_HOME%\lib\slf4j-api-1.6.1.jar

@rem Execute otjavabackend
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %OTJAVABACKEND_OPTS%  -classpath "%CLASSPATH%" com.tubtale.otbackend.StandaloneServer %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable OTJAVABACKEND_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%OTJAVABACKEND_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
