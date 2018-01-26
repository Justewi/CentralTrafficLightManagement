#!/bin/bash

EXEC_PATH="$PWD"
SCRIPT_PATH="$(cd "$(dirname "$0")" && pwd)"

cd "$SCRIPT_PATH"
mkdir build
cd build
cmake ..
make
cp controler ..
cd ..
rm -rf build

cd "$EXEC_PATH"
