FROM maven

WORKDIR /root

ADD pom.xml /root/pom.xml
ADD mvnw /root
ADD .mvn/wrapper/maven-wrapper.properties /root/.mvn/wrapper/maven-wrapper.properties
ADD target /root/target

RUN mvn dependency:sources

ENTRYPOINT [ "./mvnw", "spring-boot:run", "-X" ]
