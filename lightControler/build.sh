#!/bin/bash

mkdir build
cd build

cmake ..
make

cp lightCtrlR ..
cd ..

rm -rf build