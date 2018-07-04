0)
scp -r pre-test xmrig@192.168.1.101:/home/xmrig
sudo keytool -import -trustcacerts -file /home/xmrig/pre-test/server.cer -alias tomcat-client -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit

scp -r pre-test xmrig@192.168.1.101:/usr/bin/xmrig-sys


1) install cert to java keytool

sudo keytool -import -trustcacerts -file /home/vkotsiuba/IdeaProjects/education/monitoring-app/client/src/main/resources/server.cer -alias tomcat-client -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit
sudo keytool -import -trustcacerts -file /home/koviiv/IdeaProjects/education/monitoring-app/client/src/main/resources/server.cer -alias tomcat-client -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit

sudo keytool -import -trustcacerts -file /home/xmrig1/pre-test/server.cer -alias tomcat-client -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit
sudo keytool -import -trustcacerts -file /home/xmrig2/pre-test/server.cer -alias tomcat-client -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit
sudo keytool -import -trustcacerts -file /home/xmrig3/pre-test/server.cer -alias tomcat-client -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit
sudo keytool -import -trustcacerts -file /home/xmrig4/pre-test/server.cer -alias tomcat-client -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit


sudo keytool -import -trustcacerts -file /usr/bin/xmrig-sys/temperature_monitor/server.cer -alias tomcat-client -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit
/usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java -jar /usr/bin/xmrig-sys/temperature_monitor/client-1.0-SNAPSHOT.jar > /dev/null 2>&1