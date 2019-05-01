#!/bin/bash

service=$1

baseImagePath="softbei"

echo "----------------------------------------------------------------------"
cd ${service}

git pull

sh mvn.sh

docker rmi -f ${baseImagePath}/${service}:latest

docker build -t ${baseImagePath}/${service} .

echo "----------------------------------------------------------------------"
