#!/bin/sh

nb=3

[[ $# -ge 1 ]] && nb=$1

docker run --network=street_network \
  -it --rm \
  -e "NB=$nb" -e "CS=activemq" -e "CS_PORT=5672" \
  --name=controllers \
  gr2/controller
    