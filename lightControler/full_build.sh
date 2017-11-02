#!/bin/bash

cd docker_builder
./build.sh
cd ..

docker build -t gr7/controller