# Check a better image to depend on
FROM tomcat:9.0

WORKDIR /root

RUN \
  apt-get update -y && \
  apt-get upgrade -y && \
  apt-get install -y maven

ADD * /root
ADD mvnw /root
ADD .mvn /root
ADD target /root

RUN mvn dependency:sources