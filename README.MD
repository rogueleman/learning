2.4 Vagrant

2.4.1 Install boxes
vagrant box add saucy64 http://cloud-images.ubuntu.com/vagrant/saucy/current/saucy-server-cloudimg-amd64-vagrant-disk1.box
vagrant box add centos59 http://puppet-vagrant-boxes.puppetlabs.com/centos-59-x64-vbox4210-nocm.box

This commands should install vagrant boxes in your home directory

ls -la --color ~/.vagrant.d/boxes/

2.4.2 Running VMs
In the CLIENT_DATA_HOME/vagrant directory, run the following command
vagrant up
After few moments, the VM should be started ;)
Check the CLIENT_DATA_HOME/vagrant/Vagrantfile file to see what is installed on the VMs
You should have now a PostgreSql and an ActiveMQ running (each have its own VM).
PostgresSql can be accessed at this address : jdbc:postgresql://192.168.255.4:5432/fdm (dev/dev@123)
ActiveMq admin can be accessed at this url : http://192.168.255.5:8161/admin/ (admin/admin)

2.5 FDM

2.5.1 Initialize database
Some data must exists in your database (common schema, templates, functions)
To initialize your database, add execution permission to the script initDb.sh in your CLIENT_DATA_HOME directory and execute it
chmod +x initDb.sh 
./initDb.sh
[edit]