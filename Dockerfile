# TODO Check a better image to depend on
FROM tomcat:9.0

WORKDIR /root

RUN /usr/local/tomcat/bin/shutdown.sh

RUN \
  apt-get update -y && \
  apt-get upgrade -y && \
  apt-get install -y maven

ADD pom.xml /root/pom.xml
ADD mvnw /root
ADD .mvn/wrapper/maven-wrapper.properties /root/.mvn/wrapper/maven-wrapper.properties
ADD target /root/target

RUN mvn dependency:sources