#!/usr/bin/env bash

EXEC_PATH="$PWD"
SCRIPT_PATH="$(cd "$(dirname "$0")" && pwd)"

cd "$SCRIPT_PATH"
cd ..
docker network create street_network
docker-compose up -d mymongo
sleep 3

cd "$SCRIPT_PATH"
mvn clean package -DskipTests=true
docker build -t gr2/ctlms .
cd ..

docker-compose down
#docker network rm street_network

cd "$EXEC_PATH"
