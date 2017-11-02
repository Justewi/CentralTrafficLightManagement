#!/usr/bin/env bash

mvn clean package

docker build -t gr2/ctlms .