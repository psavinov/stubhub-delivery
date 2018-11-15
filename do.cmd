@ECHO OFF

IF "%1" EQU "install" GOTO install
IF "%1" EQU "test" GOTO test
IF "%1" EQU "run" GOTO run

ECHO "Please run do install first and then do test or do run"
GOTO end

:install
cd stubhub-delivery-core
mvn clean install
cd ..\stubhub-delivery-inject
mvn clean install
cd ..\stubhub-delivery-impl
mvn clean install
cd ..\
GOTO end

:test
cd stubhub-delivery-test
mvn clean install
cd ..\
GOTO end

:run
cd stubhub-delivery-simulation
mvn clean install
mvn exec:exec %2 %3 %3 %5 %6
cd ..\
GOTO end

:end
exit
