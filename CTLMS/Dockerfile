FROM maven:3.5-jdk-8-alpine
MAINTAINER gr7@AL

COPY target/smartcity-ctlms.jar .

CMD java -jar -Dspring.profiles.active=docker smartcity-ctlms.jar $RABBIT_HOST