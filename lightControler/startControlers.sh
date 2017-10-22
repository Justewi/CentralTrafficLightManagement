#!/bin/bash
#
# File:   startControlers.bash
# Start several controlers.
#

serverAddr=""
serverPort=""

[[ $# -eq 0 ]] && echo "Missing parameter, usage: $0 numberOfCtrl [serverAddr] [serverPort]" && exit 1;
[[ $# -gt 1 ]] && serverAddr=$2
[[ $# -gt 2 ]] && serverAddr=$3

for ((i = 1; i <= $1; i++)); do
    ./controler "ctrl$i" $serverAddr $serverPort &
done