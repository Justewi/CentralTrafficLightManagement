#!/bin/sh

from=1
to=3
cs=activemq
cs_port=5672

[[ $# -ge 1 ]] && from=$1
[[ $# -ge 2 ]] && to=$2
[[ $# -ge 3 ]] && cs=$3
[[ $# -ge 4 ]] && cs_port=$4

docker run --network=street_network \
  -it --rm \
  -e "FROM=$from" -e "TO=$to" -e "CS=$cs" -e "CS_PORT=$cs_port" \
  --name=controllers \
  gr2/controller
    