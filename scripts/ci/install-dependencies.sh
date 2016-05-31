#!/usr/bin/env bash

set -x
set -e

if [ ! -e mongodb-linux-x86_64-ubuntu1404-3.2.6 ]; then
    sudo service mongod stop
    curl -L -O https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-ubuntu1404-3.2.6.tgz
    tar xvzf mongodb-linux-x86_64-ubuntu1404-3.2.6.tgz
    sudo mv mongodb-linux-x86_64-ubuntu1404-3.2.6/bin/* /usr/bin/
    sudo service mongod start
fi

gradle_version=$(echo $(gradle -version) | awk '{ print $3; }')

if [[ "$gradle_version" < "2.10" ]]; then
      sudo add-apt-repository ppa:cwchien/gradle -y
      sudo apt-get clean
      sudo apt-get update
      sudo apt-get --purge remove gradle
      sudo apt-get install gradle
fi