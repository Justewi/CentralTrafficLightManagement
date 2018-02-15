#!/usr/bin/env bash

../lightControler/dockerRun.sh &

java -jar ../CTLMS/target/smartcity-ctlms.jar &


sleep 10