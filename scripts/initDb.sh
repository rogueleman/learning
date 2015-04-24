#!/bin/bash
# To bypass entering the password for each script, edit or create the file ~/.pgpass and add the line:
# 192.168.255.4:5432:*:dev:dev@123

basepath="$PWD"
hostname=192.168.255.4
user=dev
db=dictionar
scriptsFolder="${basepath}/database"

function executeScript(){
    echo "Execute script: $1";
    psql -h ${hostname} -U ${user} -d ${db} -p 5432 -a -f ${scriptsFolder}/$1;
    if [ $? -ne 0 ] ; then
        echo "Error when executing $1";
        exit 1;
    fi
}

executeScript "create_dictionar_tables.sql"
executeScript "populate_dictionar_tables.sql"

exit 0;
