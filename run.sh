#!/bin/bash
RED='\033[0;31m'
ORANGE='\033[0;33m'
NC='\033[0m'

docker network create street_network

docker-compose up -d activemq
docker-compose up -d mymongo

echo -e "${ORANGE}\nWaiting for activeMQ to complete startup${NC}\n"
sleep 5

#docker-compose up ctlms
java -jar CTLMS/target/smartcity-ctlms.jar

#echo -e "${ORANGE}\nWaiting for CTLMS to complete startup${NC}\n"
#sleep 5

#docker-compose run controller

read -p "Press enter to continue"

echo -e "${RED}\nShutting down the whole system${NC}\n"
docker-compose down
docker network rm street_network
