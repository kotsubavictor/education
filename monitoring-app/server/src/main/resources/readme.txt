java -jar target/spr...

0) Mosquitto MQTT SERVER
sudo apt-get install mosquitto mosquitto-clients
mosquitto_sub -h localhost -t test
mosquitto_pub -h localhost -t test -m "hello world"


1)
 keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650

Enter keystore password:
 Re-enter new password:
 What is your first and last name?
 [Unknown]:
 What is the name of your organizational unit?
 [Unknown]:
 What is the name of your organization?
 [Unknown]:
 What is the name of your City or Locality?
 [Unknown]:
 What is the name of your State or Province?
 [Unknown]:
 What is the two-letter country code for this unit?
 [Unknown]:
 Is CN=Unknown, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown correct?
 [no]: yes

 123456


 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-18 12:57:06', 'xmrig1', 18.9333, 60, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-18 12:52:06', 'xmrig3', 18.7797, 67, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-18 12:52:06', 'xmrig4', 18.1017, 59, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-18 12:52:06', 'xmrig1', 18.2, 60, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-18 12:57:06', 'xmrig3', 18.65, 67, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-18 12:57:06', 'xmrig4', 18.0167, 59, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-18 12:46:58', 'xmrig3', 18.6667, 66, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-18 12:46:58', 'xmrig4', 18, 59, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-18 12:46:58', 'xmrig1', 18.3333, 60, 0);

 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-17 12:57:06', 'xmrig1', 17.9333, 60, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-17 12:52:06', 'xmrig3', 17.7797, 67, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-17 12:52:06', 'xmrig4', 17.1017, 59, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-17 12:52:06', 'xmrig1', 17.2, 60, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-17 12:57:06', 'xmrig3', 17.65, 67, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-17 12:57:06', 'xmrig4', 17.0167, 59, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-17 12:46:58', 'xmrig3', 17.6667, 66, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-17 12:46:58', 'xmrig4', 17, 59, 0);
 INSERT INTO server.temperature (snapshot, name, avg, max, min) VALUES ('2018-05-17 12:46:58', 'xmrig1', 17.3333, 60, 0);

USE 32 bit sdk only
mvn install:install-file -DgroupId=javax.comm -DartifactId=comm -Dversion=3.0-u1  -Dpackaging=jar -Dfile=/home/koviiv/IdeaProjects/education/monitoring-app/server/libraries/comm.jar
cp libLinuxSerialParallel.so /usr/lib
Copy the file javax.comm.properties to your <JDK>/jre/lib or your <JRE>/lib directory.

lpinfo -v
add serial port javax.comm.properties
sudo apt-get install execstack
sudo execstack -c libLinuxSerialParallel.so
ls -l /dev/ttyUSB0
getent group | grep dia
useradd -g dialout koviiv
sudo usermod -a -G dialout koviiv
groups