#!/bin/bash
echo "停止应用"
#source stop.sh
PID=$(ps -ef | grep apidoc-1.0.0.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
    echo Application is already stopped
else
    echo kill $PID
    kill $PID
fi

echo "启动应用"
#  source start.sh
nohup java -jar /home/ci/.jenkins/workspace/apidoc/target/apidoc-1.0.0.jar &
