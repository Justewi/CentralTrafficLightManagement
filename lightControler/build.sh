#!/bin/bash

mkdir build
cd build
cmake ..
make
cp controler ..
cd ..
rm -rf build
