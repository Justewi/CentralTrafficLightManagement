#!/bin/bash

cd docker_builder
./build.sh
cd ..

./app_build.sh