#!/usr/bin/env bash

EXEC_PATH="$PWD"
SCRIPT_PATH="$(cd "$(dirname "$0")" && pwd)"

cd "$SCRIPT_PATH"
mvn clean package

docker build -t gr2/ctlms .
cd "$EXEC_PATH"