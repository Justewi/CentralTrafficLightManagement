#!/usr/bin/env bash

cd AMQP-CPP

sudo make install

cd ..
sudo mkdir /usr/include/nlohmann
sudo cp nlohmann_json/json.hpp /usr/include/nlohmann/json.hpp

make
