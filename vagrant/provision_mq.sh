#!/bin/sh
wget http://192.168.0.200/documentation/00_installation_files/RPMS/x86_64/jdk7-dev-1.7.0_21-1.emv.x86_64.rpm
rpm -i jdk7-dev-1.7.0_21-1.emv.x86_64.rpm

echo 'export JAVA_HOME=/usr/java/jdk' >> /etc/profile
echo 'PATH=$JAVA_HOME/bin:$PATH' >> /etc/profile

wget http://192.168.0.200/documentation/00_installation_files/RPMS/x86_64/apache-activemq-5.8.0-1.emv.noarch.rpm
rpm -i apache-activemq-5.8.0-1.emv.noarch.rpm

/sbin/chkconfig iptables off
/etc/init.d/iptables stop

/etc/init.d/activemq start