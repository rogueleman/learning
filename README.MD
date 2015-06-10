1 Project

examples of call
http://192.168.33.11:8080/dictionar-core/word/random

http://192.168.33.11:8080/anagram/jsp/AnagramStart.jsp


2.4 Vagrant

2.4.1 Install boxes
vagrant box add precise32 http://files.vagrantup.com/precise32.box
vagrant box add tomcatManu tomcatMANU_bon.box (see dropbox Smartfocus/vagrant)

This commands should install vagrant boxes in your home directory

ls -la --color ~/.vagrant.d/boxes/

2.4.2 Running VMs
In the PROJECT_HOME/vagrant directory, run the following command
vagrant up
After few moments, the VM should be started ;)
Check the PROJECT_HOME/vagrant/Vagrantfile file to see what is installed on the VMs
You should have now a PostgreSql and a Tomcat running (each have its own VM).
PostgresSql can be accessed at this address : jdbc:postgresql://192.168.255.4:5432/dictionar (dev/dev@123)
Tomcat can be accessed at this url : http://192.168.33.11:8080 (you can see http://blog.cmdbase.io/easy-railo-development-with-vagrant/)


2.5 Dictionar

2.5.1 Initialize database
Some data can exist in your database (word)
To initialize your database, add execution permission to the script initDb.sh in your PROJECT_HOME/scripts directory and
execute it
chmod +x initDb.sh
./initDb.sh


2.6 Utils
2.6.1 Connect to VMs
go where your Vagrantfile is
vagrant ssh tomcatManu

2.6.2 Reload VM
http://docs.vagrantup.com/v2/cli/reload.html
vagrant provision --provision-with tomcat

2.6.3 War on tomcat VM
mvn clean install -Pvagrant-deploy
vagrant provision --provision-with copy_war tomcatManu

- second version
cp /local00/sources/learning/dictionar-core-api/dictionar-core-api-server/target/dictionar-core.war
/local00/sources/learning/vagrant/webapps
vagrant ssh tomcatManu
sudo cp /vagrant/webapps/* /usr/local/tomcat/webapps/


3. Issues
3.1 Authentication failure. Retrying...
vagrant up tomcatManu
Bringing machine 'tomcatManu' up with 'virtualbox' provider...
==> tomcatManu: Importing base box 'manutomcat'...
==> tomcatManu: Matching MAC address for NAT networking...
==> tomcatManu: Setting the name of the VM: vagrant_tomcatManu_1433146960184_10795
==> tomcatManu: Clearing any previously set network interfaces...
==> tomcatManu: Preparing network interfaces based on configuration...
    tomcatManu: Adapter 1: nat
    tomcatManu: Adapter 2: hostonly
==> tomcatManu: Forwarding ports...
    tomcatManu: 22 => 2222 (adapter 1)
==> tomcatManu: Running 'pre-boot' VM customizations...
==> tomcatManu: Booting VM...
==> tomcatManu: Waiting for machine to boot. This may take a few minutes...
    tomcatManu: SSH address: 127.0.0.1:2222
    tomcatManu: SSH username: vagrant
    tomcatManu: SSH auth method: private key
    tomcatManu: Warning: Connection timeout. Retrying...
    tomcatManu: Warning: Authentication failure. Retrying...
    tomcatManu: Warning: Authentication failure. Retrying...
    tomcatManu: Warning: Authentication failure. Retrying...
    tomcatManu: Warning: Authentication failure. Retrying...
    tomcatManu: Warning: Authentication failure. Retrying...
    tomcatManu: Warning: Authentication failure. Retrying...
    tomcatManu: Warning: Authentication failure. Retrying...
    tomcatManu: Warning: Authentication failure. Retrying...
    tomcatManu: Warning: Authentication failure. Retrying...
    tomcatManu: Warning: Authentication failure. Retrying...
    tomcatManu: Warning: Authentication failure. Retrying.


Solution:
The file '/home/vagrant/.ssh/authorized_keys' must have this at the end 'vagrant insecure public key'.
Execute:
* vagrant ssh tomcatManu
* wget --no-check-certificate  https://raw.github.com/mitchellh/vagrant/master/keys/vagrant.pub -O /home/vagrant/.ssh/authorized_keys

3.2 eth1 not created

The following SSH command responded with a non-zero exit status.
Vagrant assumes that this means the command failed!

ARPCHECK=no /sbin/ifup eth1 2> /dev/null

Stdout from the command:

Device eth1 does not seem to be present, delaying initialization.

Stderr from the command:

Solution: (given by istvan-ujjmeszaros - https://github.com/mitchellh/vagrant/issues/2614)
I have to fix this issue manualy after every box update, which is really annoying.
These are the steps I make:

]$ vagrant ssh
]$ sudo su -

]$ rm -rf /etc/udev/rules.d/70-persistent-net.rules
]$ rm -rf /etc/sysconfig/network-scripts/ifcfg-eth1

]$ vim /etc/sysconfig/network-scripts/ifcfg-eth1
DEVICE=eth1
ONBOOT=yes

]$ exit
]$ exit

]$ vagrant reload
]$ vagrant ssh

]$ sudo su -
]$ vim /etc/sysconfig/network-scripts/ifcfg-eth1

# you will see the Vagrant generated code
# remove what you put in there; leave the generated stuff

]$ exit
]$ exit

]$ vagrant reload

3.3
There are errors in the configuration of this machine. Please fix
the following errors and try again:

vm:
* The '' provisioner could not be found.

Solution:
vagrant plugin install vagrant-hosts
