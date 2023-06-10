FROM amazon/aws-cli:latest as appdynamics

ARG AWS_DEFAULT_REGION
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY
ARG AWS_SESSION_TOKEN
ARG AWS_CONTAINER_CREDENTIALS_RELATIVE_URI

RUN mkdir -p /app/appdynamics/
#RUN aws s3 cp s3://appdynamics-installers/maven/openjdk11/saas/AppServerAgent.zip /app/appdynamics

FROM alpine:latest

RUN mkdir -p /app
#RUN apk add --nocache ca-certificates
#COPY ca_bundle.crt /usr/local/share/ca-certificates/ca_bundle.crt
#RUN update-ca-certificates

RUN apk --update add openjdk17

RUN addgroup -S spring && adduser -S spring -G spring

#RUN mkdir -p /app/appdynamics/
#COPY --from=appdynamics /app/appdynamics /app/appdynamics

COPY /target/*.jar /app/app.jar
RUN ls

WORKDIR /app
RUN chmod 777 /app
RUN chown -R spring:spring /app
USER spring:spring

#RUN ls

#RUN unzip /app/appdynamics/AppServerAgent.zip -d /app/appdynamics/ \
#&& rm -f /app/appdynamics/AppServerAgent.zip

# ENTRYPOINT ["sh", "-c", "java -javaagent:/app/appdynamics/javaagent.jar -Xms256m -Xmx512m -noverify -XX:TieredStopAtLevel=1 -Duser.timezone=America/Sao_Paulo -Duser.language=pt -Duser.region=BR -Duser.country=BR -Dspring.jmx.enabled=true -Dspring.profiles.active=$SPRING_PROFILE app.jar"]
#ENTRYPOINT ["sh", "-c", "java -Xms256m -Xmx512m -noverify -XX:TieredStopAtLevel=1 -Duser.timezone=America/Sao_Paulo -Duser.language=pt -Duser.region=BR -Duser.country=BR -Dspring.jmx.enabled=true -Dspring.profiles.active=$SPRING_PROFILE app.jar"]
#ENTRYPOINT ["sh", "-c", "java -Xms256m -Xmx512m -noverify -XX:TieredStopAtLevel=1 -Duser.timezone=America/Sao_Paulo -Duser.language=pt -Duser.region=BR -Duser.country=BR -Dspring.jmx.enabled=true -Dspring.profiles.active=$SPRING_PROFILE -cp 'BOOT-INF/classes:BOOT-INF/lib/*' br.com.kafka.KafkaSpringbootApplication"]
ENTRYPOINT ["sh", "-c", "java -Xms256m -Xmx512m -noverify -XX:TieredStopAtLevel=1 -Duser.timezone=America/Sao_Paulo -Duser.language=pt -Duser.region=BR -Duser.country=BR -Dspring.jmx.enabled=true -Dspring.profiles.active=$SPRING_PROFILE -jar app.jar"]



#ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8090

# FROM redis
# COPY redis.conf /usr/local/etc/redis/redis.conf
# CMD [ "redis-server", "/usr/local/etc/redis/redis.conf" ]


##### RODE OS COMANDOS
#####  docker build -t spring-kafka .
#####  docker run spring-kafka