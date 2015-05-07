#!/bin/bash

function executeScript(){
    sudo mkdir -p /etc/emv/anagram
    sudo mkdir -p /etc/emv/dictionar-core
    sudo cp /vagrant/properties/anagram/* /etc/emv/anagram
    sudo cp /vagrant/properties/dictionar-core/* /etc/emv/dictionar-core
}

executeScript

exit 0;
