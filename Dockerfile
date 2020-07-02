FROM java:8

VOLUME /tmp

EXPOSE 8080

ADD /target/marvel-0.0.1-SNAPSHOT.jar marvel-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","marvel-0.0.1-SNAPSHOT.jar"]
