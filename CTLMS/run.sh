#!/usr/bin/env bash

echo "Launching rabbitmq docker image ..."
docker run -d -p 5672:5672 rabbitmq:3.6

echo "Starting java server ..."
mvn exec:java