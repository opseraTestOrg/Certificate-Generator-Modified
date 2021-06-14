#!/bin/bash
nohup java -jar -Xms128m -Xmx256m -Dspring.profiles.active=$1 /apps/OpsERA/components/certificate-generator/live/certificate-generator-$2.jar &
echo $! > ././pid.file