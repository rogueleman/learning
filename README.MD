1 Project




2.4 Vagrant

2.4.1 Install boxes
vagrant box add saucy64 http://cloud-images.ubuntu.com/vagrant/saucy/current/saucy-server-cloudimg-amd64-vagrant-disk1.box
vagrant box add centos59 http://puppet-vagrant-boxes.puppetlabs.com/centos-59-x64-vbox4210-nocm.box
vagrant box add precise32 http://files.vagrantup.com/precise32.box
vagrant box add manutomcat SF_Centos6_Tomcat.box

This commands should install vagrant boxes in your home directory

ls -la --color ~/.vagrant.d/boxes/

2.4.2 Running VMs
In the CLIENT_DATA_HOME/vagrant directory, run the following command
vagrant up
After few moments, the VM should be started ;)
Check the PROJECT_HOME/vagrant/Vagrantfile file to see what is installed on the VMs
You should have now a PostgreSql and an ActiveMQ running (each have its own VM).
PostgresSql can be accessed at this address : jdbc:postgresql://192.168.255.4:5432/dictionar (dev/dev@123)
ActiveMq admin can be accessed at this url : http://192.168.255.5:8161/admin/ (admin/admin)
Tomcat can be accessed at this url : http://192.168.33.10:8080 (you can see http://blog.cmdbase.io/easy-railo-development-with-vagrant/)


2.5 Dictionar

2.5.1 Initialize database
Some data must exists in your database (word, word_anagrams)
To initialize your database, add execution permission to the script initDb.sh in your PROJECT_HOME/scripts directory and
execute it
chmod +x initDb.sh
./initDb.sh


2.6 Utils
2.6.1 Connect to VMs
go where your Vagrantfile is
vagrant ssh tomcat

2.6.2 Reload VM
http://docs.vagrantup.com/v2/cli/reload.html
vagrant provision --provision-with tomcat

2.6.3 War on Vm tomcat
cp /local00/sources/learning/dictionar-core-api/dictionar-core-api-server/target/dictionar-core.war
/local00/sources/learning/vagrant/webapps
vagrant ssh tomcat
sudo cp /vagrant/webapps/* /var/lib/tomcat7/webapps/






