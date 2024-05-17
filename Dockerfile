FROM buildo/h2database AS db
COPY /src/main/resources/import.sql /docker-entrypoint-initdb.d/

FROM maven:3.6.3-jdk-11 as build
ENV APP_HOME=/usr/src/app
RUN mkdir -p $APP_HOME
COPY . $APP_HOME
WORKDIR $APP_HOME
RUN mvn clean package

FROM tomcat:9.0.41-jdk11-openjdk as webapp
COPY --from=build /usr/src/app/target/20_spring.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
ENTRYPOINT ["catalina.sh", "run"]



