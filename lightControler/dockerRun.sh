#!/bin/sh

nb=3
cs=activemq
cs_port=5672

[[ $# -ge 1 ]] && nb=$1
[[ $# -ge 2 ]] && cs=$2
[[ $# -ge 3 ]] && cs_port=$3

docker run --network=street_network \
  -it --rm \
  -e "NB=$nb" -e "CS=$cs" -e "CS_PORT=$cs_port" \
  --name=controllers \
  gr2/controller
    