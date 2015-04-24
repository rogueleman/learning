#!/bin/sh
apt-get install -y -q language-pack-en
cat >> /etc/bash.bashrc <<EOF
export LANGUAGE=en_US.UTF-8
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8
EOF
locale-gen en_US.UTF-8
dpkg-reconfigure locales

apt-get install -y python-pip python-dev

pip install vaurien

mkdir /var/lock/chaosmonkey
mkdir /etc/chaosmonkey

cat > /etc/chaosmonkey/postgresql.sh <<EOF
#!/bin/bash
TARGET_HOST=192.168.255.4
THIS_HOST=192.168.255.3

PROXY=\$THIS_HOST:5433
BACKEND=\$TARGET_HOST:5432
HTTP_HOST=\$THIS_HOST
HTTP_PORT=5434

nohup vaurien --proxy "\$PROXY" --backend "\$BACKEND" --protocol-tcp-keep-alive --stay-connected --http --http-host "\$HTTP_HOST" --http-port "\$HTTP_PORT" &
EOF

cat > /etc/chaosmonkey/activemq.sh <<EOF
#!/bin/bash
TARGET_HOST=192.168.255.5
THIS_HOST=192.168.255.3

PROXY=\$THIS_HOST:61617
BACKEND=\$TARGET_HOST:61616
HTTP_HOST=\$THIS_HOST
HTTP_PORT=61618

nohup vaurien --proxy "\$PROXY" --backend "\$BACKEND" --protocol-tcp-keep-alive --stay-connected --http --http-host "\$HTTP_HOST" --http-port "\$HTTP_PORT" &
EOF

chmod +x /etc/chaosmonkey/*.sh

cat > /etc/init.d/chaosmonkey <<EOF
#!/bin/bash

CHAOSMONKEY_CFG_DIR=/etc/chaosmonkey

case "\$1" in
    start)
        for script in "\$CHAOSMONKEY_CFG_DIR"/*
        do
            if [[ -x "\$CHAOSMONKEY_CFG_DIR" ]]; then
                "\$script"
            fi
        done
        touch /var/lock/chaosmonkey
        ;;
    stop)
        ps -aef |grep vaurien | awk '{print \$2}' | xargs kill -9
        rm -f /var/lock/chaosmonkey
        ;;
    reload|restart)
        \$0 stop
        \$0 start
        ;;
    *)
        echo "Usage: \$0 start|stop|restart|reload"
        exit 1
esac
exit 0
EOF

chmod +x /etc/init.d/chaosmonkey

update-rc.d chaosmonkey defaults

service chaosmonkey start