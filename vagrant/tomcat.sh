sudo apt-get -y update
sudo apt-get -y install tomcat7
mkdir -p /etc/emv/anagram
mkdir -p /etc/emv/dictionar-core
cp /vagrant/properties/anagram/* /etc/emv/anagram
cp /vagrant/properties/dictionar-core/* /etc/emv/dictionar-core