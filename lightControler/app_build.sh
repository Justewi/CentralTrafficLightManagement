#!/usr/bin/env bash

./build.sh
#dos2unix startControlers.sh

docker build -t gr2/controller .
