FROM tomcat:9.0-jdk11

ARG BUILD_NUMBER

RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/petstore-mybuildnumber.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
