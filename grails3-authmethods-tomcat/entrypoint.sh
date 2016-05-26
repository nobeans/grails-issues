#!/bin/bash -xe

WAR_FILE=/usr/src/app/build/libs/*.war

ls $WAR_FILE >/dev/null 2>&1
if [ ! $? -eq 0 ]; then
    echo "WAR file not found: $WAR_FILE"
    exit 1
fi

# Delete default webapps
rm -r $CATALINA_HOME/webapps/*

# for DEBUG (requires default webapps)
#echo '<?xml version="1.0" encoding="utf-8"?><tomcat-users><role rolename="manager-gui"/><user username="tomcat" password="tomcat" roles="manager-gui"/></tomcat-users>' > $CATALINA_HOME/conf/tomcat-users.xml

# Deploy WAR file
cp $WAR_FILE $CATALINA_HOME/webapps/app.war

# The "egd" setting is to speed up Tomcat startup by giving it a faster source of entropy for session keys.
# https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html
export JAVA_OPTS="$JAVA_OPTS -Dgrails.env=prod -Djava.security.egd=file:/dev/./urandom"

$CATALINA_HOME/bin/catalina.sh run
