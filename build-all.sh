#!/bin/bash


path=`cd $(dirname $0);pwd -P`
echo the current path is:$path
echo '------------'
for service in `ls`;
do
  if [ -d $service ];then
    echo "####################开始 ${service}###############################"
    sh build.sh $service 
    echo "####################结束 ${service}###############################"
  fi;
done
