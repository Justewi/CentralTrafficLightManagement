#!/bin/bash
RED='\033[0;31m'
ORANGE='\033[0;33m'
NC='\033[0m'

docker-compose up -d activemq

echo -e "${ORANGE}\nWaiting for activeMQ to complete startup${NC}\n"
sleep 5

docker-compose up ctlms

echo -e "${ORANGE}\nWaiting for CTLMS to complete startup${NC}\n"
sleep 5

docker-compose run controller

echo -e "${RED}\nShutting down the whole system${NC}\n"
docker-compose down
