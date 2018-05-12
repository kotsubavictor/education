0)
scp -r pre-test xmrig1@192.168.1.101:/home/xmrig1
scp -r pre-test xmrig2@192.168.1.102:/home/xmrig2
scp -r pre-test xmrig3@192.168.1.112:/home/xmrig3
scp -r pre-test xmrig4@192.168.1.107:/home/xmrig4

1) install cert to java keytool

sudo keytool -import -trustcacerts -file /home/vkotsiuba/IdeaProjects/education/monitoring-app/client/src/main/resources/server.cer -alias tomcat-client -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit
sudo keytool -import -trustcacerts -file /home/koviiv/IdeaProjects/education/monitoring-app/client/src/main/resources/server.cer -alias tomcat-client -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit

sudo keytool -import -trustcacerts -file /home/xmrig1/pre-test/server.cer -alias tomcat-client -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit
sudo keytool -import -trustcacerts -file /home/xmrig2/pre-test/server.cer -alias tomcat-client -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit
sudo keytool -import -trustcacerts -file /home/xmrig3/pre-test/server.cer -alias tomcat-client -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit
sudo keytool -import -trustcacerts -file /home/xmrig4/pre-test/server.cer -alias tomcat-client -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit