#!/usr/bin/env bash

set -x
set -e

if [ ! -e mongodb-linux-x86_64-3.2.6/bin ]; then
    sudo service mongod stop
    curl -L -O https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-ubuntu1404-3.2.6.tgz
    tar xvzf mongodb-linux-x86_64-ubuntu1404-3.2.6.tgz
    sudo mv mongodb-linux-x86_64-ubuntu1404-3.2.6/bin/* /usr/bin/
    sudo service mongod start
fi

gradle_version=$(echo $($GRADLE_HOME/bin/gradle -version) | awk '{ print $3; }')

if [[ "$gradle_version" > "2.10" ]]; then
    echo "Installed"

else
    echo "Not installed"
fi