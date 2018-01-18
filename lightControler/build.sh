#!/bin/bash

mkdir build
cd build
cmake ..
make
cp lightCtrlR ../controler
cd ..
rm -rf build
