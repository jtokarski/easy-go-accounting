FROM tomcat:10.1.6-jre17-temurin-jammy

ADD --chmod=644 \
  ./web-application/src/main/resources/easygoKs \
  ./docker-infra/services/run-tomcat-web/server.xml \
  /usr/local/tomcat/conf/

ADD --chmod=644 \
  ./docker-infra/services/run-tomcat-web/context.xml \
  /usr/local/tomcat/conf/Catalina/localhost/easygo-web.xml

ADD --chmod=644 \
  ./web-application/build/libs/web-application-plain.war \
  /usr/local/tomcat/webapps/easygo-web.war

