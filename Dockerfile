FROM openjdk:11
WORKDIR /
ADD target/ankifill-0.0.1-SNAPSHOT.jar ankifill-0.0.1-SNAPSHOT.jar
CMD java -jar ankifill-0.0.1-SNAPSHOT.jar
EXPOSE 8080