## JMS module. Requirements.
* Maven
* Java 17
* Docker 

### For running ActiveMQ standalone instance use Docker command below.
* docker run -d -p 61616:61616 -p 8161:8161 --name activemq webcenter/activemq

After container runs ActiveMQ will be accessed by localhost:8161. Use default creds
* login: admin
* password: admin

### Also needs to run services from CLI. Use jar files for that and commands below:

#### UI service for creating orders via CLI 
- java -jar order-ui-service-0.0.1-SNAPSHOT.jar

#### Processing order service for automatically sorting orders to ACCEPTED and REJECTED by certain rules
* java -jar order-processing-service-0.0.1-SNAPSHOT.jar

#### Report service witch writing accepted and rejected orders in appropriate files, in this case to CSV files
* java -jar order-reporter-service-0.0.1-SNAPSHOT.jar
