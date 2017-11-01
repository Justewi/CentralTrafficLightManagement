#!/usr/bin/env bash

# Lancer "./build.sh" pour se connecter au serveur RabbitMQ en localhost
# Pour changer l'ip emplacement du serveur : "./build.sh <IP>"

git clone https://github.com/CopernicaMarketingSoftware/AMQP-CPP.git

dos2unix buildInContainer.sh
dos2unix startControlers.sh

#mkdir nlohmann_json
#curl https://github.com/nlohmann/json/releases/download/v2.1.1/json.hpp > nlohmann_json/json.hpp

docker build -t ctlms/light-controler .