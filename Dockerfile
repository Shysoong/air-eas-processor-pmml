FROM mcr.microsoft.com/java/jdk:8-zulu-alpine

MAINTAINER tony@skyease.io

ARG modelFileName

ARG modelPath

ARG modelName

ENV e_modelName=${modelName}

ENV e_modelFileName=${modelFileName}

RUN mkdir -p /eas

WORKDIR /eas

ADD ${modelPath} ./modelFile/

EXPOSE 8099

ADD http://58.220.240.50:22101/eas-seed/processor/pmml/processor.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.application.name=${e_modelName}" , "-jar", "app.jar"]

CMD ["--spring.profiles.active=test", "--processor.modelFileName=${e_modelFileName}"]
