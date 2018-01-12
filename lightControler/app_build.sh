#!/usr/bin/env bash

dos2unix build.sh
dos2unix startControlers.sh

docker build -t gr2/controller .