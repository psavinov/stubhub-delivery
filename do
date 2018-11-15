#!/bin/sh
#
if [ "$1" == "install" ]; then
    cd stubhub-delivery-core
    mvn clean install
    cd ../stubhub-delivery-inject
    mvn clean install
    cd ../stubhub-delivery-impl
    mvn clean install
    cd ../
elif [ "$1" == "test" ]; then
    cd stubhub-delivery-test
    mvn clean install
    cd ../
elif [ "$1" == "run" ]; then
    cd stubhub-delivery-simulation
    mvn clean install
    mvn exec:exec $2 $3 $4 $5 $6
    cd ../
else 
    echo "Please run './do install' first and then './do test' or './do run'"
fi
