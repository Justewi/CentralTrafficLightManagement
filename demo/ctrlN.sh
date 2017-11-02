#!/bin/sh

docker run --network=street_network \
  -it --rm \
  -e "ID=ctrl${1}" -e "CS=activemq" -e "CS_PORT=5672" \
  --name=ctrl${1} \
  gr2/controller
