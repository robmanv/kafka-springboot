# kafka-springboot

### COMANDO DOCKER PRA INICIAR A IMAGEM 
docker build --no-cache -t spring-kafka .
docker run -d -p 0.0.0.0:8090:8090 --name spring-kafka -e AWS_REGION=sa-east-1 -e AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID} -e AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} spring-kafka

### TESTAR QUAIS PORTAS O LOCALHOST CONSEGUE ACESSAR
curl -v localhost:<porta>

### SE NAO SE CONECTAR NA IMAGEM DOCKER ATUALIZE O HOSTS

# localhost name resolution is handled within DNS itself.
#	127.0.0.1       localhost
#	::1             localhost
# Added by Docker Desktop
192.168.15.4 host.docker.internal
192.168.15.4 gateway.docker.internal
# To allow the same kube context to work on the host and the container:
127.0.0.1 kubernetes.docker.internal
127.0.0.1 host.docker.internal
127.0.0.1 gateway.docker.internal
# End of section
