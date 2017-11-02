#!/bin/bash

cp ../docker_builder/dependencies.sh cpy_dependencies.sh
docker build -t gr2/cppbuilder .
rm cpy_dependencies.sh