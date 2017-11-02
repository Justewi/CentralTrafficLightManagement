#!/usr/bin/env bash

mkdir build
cd build
cmake ..
make

dos2unix buildInContainer.sh
dos2unix startControlers.sh

#mkdir nlohmann_json
#curl https://github.com/nlohmann/json/releases/download/v2.1.1/json.hpp > nlohmann_json/json.hpp

docker build -t ctlms/light-controler .