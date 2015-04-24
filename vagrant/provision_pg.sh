#!/bin/sh
apt-get install -y -q language-pack-en
cat >> /etc/bash.bashrc <<EOF
export LANGUAGE=en_US.UTF-8
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8
EOF
locale-gen en_US.UTF-8
dpkg-reconfigure locales

echo 'deb http://apt.postgresql.org/pub/repos/apt/ precise-pgdg main' > /etc/apt/sources.list.d/pgdg.list
wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -
apt-get update
apt-get install -y -q postgresql-9.2
pg_createcluster 9.2 main --start

#sed -i.bak 's/peer/md5/g' /etc/postgresql/9.2/main/pg_hba.conf

mv /etc/postgresql/9.2/main/pg_hba.conf /etc/postgresql/9.2/main/pg_hba.conf.orig
cat > /etc/postgresql/9.2/main/pg_hba.conf <<EOF
local   all             postgres                                peer
local   all             all             		                md5
host    all             all             0.0.0.0/0         		md5
host    all             all             ::1/128                 md5
EOF

echo "listen_addresses = '*'" >> /etc/postgresql/9.2/main/postgresql.conf

sudo -u postgres psql <<EOF
create user dev with superuser password 'dev@123';
create database dictionar;
EOF

/etc/init.d/postgresql restart