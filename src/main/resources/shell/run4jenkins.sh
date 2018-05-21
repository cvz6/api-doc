#!/bin/bash
cd /home/ci/.jenkins/workspace/apidoc/target/classes/shell
echo "停止应用"
source ./stop.sh
echo "启动应用"
source ./start.sh
