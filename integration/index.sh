#!/usr/bin/env bash

docker network create street_network

docker-compose -f ../integration.yaml up -d activemq
docker-compose -f ../integration.yaml up -d mongodb

sleep 5

./basic_scenario.sh

docker-compose down
