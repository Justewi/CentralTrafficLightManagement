#!/bin/sh

docker run --network=al_default \
  -it --rm \
  -e "ID=controller_${1}" -e "CS=activemq" -e "CS_PORT=5672" \
  --name=controller_${1} \
  gr2/controller
