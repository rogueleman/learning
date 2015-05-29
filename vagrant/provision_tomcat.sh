#!/bin/sh
sudo apt-get -y update
sudo apt-get -y install vim
#sudo apt-get -y install tomcat7

sudo mkdir -p /etc/emv/anagram
sudo mkdir -p /etc/emv/dictionar-core
sudo cp /vagrant/properties/anagram/* /etc/emv/anagram
sudo cp /vagrant/properties/dictionar-core/* /etc/emv/dictionar-core