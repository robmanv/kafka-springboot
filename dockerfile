FROM amazon/aws-cli:latest as appdynamics

ARG AWS_DEFAULT_REGION
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY
ARG AWS_SESSION_TOKEN
ARG AWS_CONTAINER_CREDENTIALS_RELATIVE_URI

RUN mkdir -p /app/appdynamics/

FROM alpine:latest

RUN mkdir -p /app
RUN apk --update add openjdk17
RUN apk add --no-cache su-exec
RUN set -ex && apk --no-cache add sudo
RUN apk add curl
RUN apk add nano
RUN addgroup -S spring && adduser -S spring -G spring
COPY /target/*.jar /app/app.jar
RUN ls
# Copie o script de espera para o contêiner
COPY wait-for-it.sh /app/wait-for-it.sh

# Dê permissões de execução ao script
RUN chmod +x /app/wait-for-it.sh

RUN su root
RUN addgroup -S sudo && adduser -S manager -G sudo
RUN getent group sudo
RUN apk update
RUN apk upgrade
RUN apk --no-cache add openssh nano sudo tzdata
RUN visudo /etc/sudoers.d/nopasswd
RUN mkdir -p /etc/sudoers.d \
        && echo "spring ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers.d/spring \
        && echo "spring ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers \
        && chmod 0440 /etc/sudoers.d/spring

WORKDIR /app
RUN chmod 777 /app
RUN chown -R spring:spring /app
USER spring:spring
EXPOSE 8090

#ENTRYPOINT ["sh", "-c", "java -Xms256m -Xmx512m -noverify -XX:TieredStopAtLevel=1 -Duser.timezone=America/Sao_Paulo -Duser.language=pt -Duser.region=BR -Duser.country=BR -Dspring.jmx.enabled=true -Dspring.profiles.active=$SPRING_PROFILE -jar app.jar"]
ENTRYPOINT ["sh", "-c", "/app/wait-for-it.sh broker 9092 java -Xms256m -Xmx512m -noverify -XX:TieredStopAtLevel=1 -Duser.timezone=America/Sao_Paulo -Duser.language=pt -Duser.region=BR -Duser.country=BR -Dspring.jmx.enabled=true -Dspring.profiles.active=$SPRING_PROFILE -jar app.jar"]
