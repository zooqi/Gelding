FROM openjdk:8
MAINTAINER Judge <zooqih@gmail.com>
WORKDIR /usr/src
COPY './target/Gelding.jar' './Gelding.jar'
CMD ["java", "-Duser.timezone=GMT+08", "-jar", "/usr/src/Gelding.jar"]
EXPOSE 8200
