1) install cert to java keytool

sudo keytool -import -trustcacerts -file /home/vkotsiuba/IdeaProjects/education/monitoring-app/client/src/main/resources/server.cer -alias tomcat-client -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit
sudo keytool -import -trustcacerts -file /home/koviiv/IdeaProjects/education/monitoring-app/client/src/main/resources/server.cer -alias tomcat-client -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit

2)