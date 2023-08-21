FROM redis:latest

COPY redis.conf /usr/local/etc/redis/redis.conf
COPY server-cert.pem /etc/ssl/server-cert.pem
COPY server-key.pem /etc/ssl/server-key.pem
COPY certificate.crt /etc/ssl/certificate.crt

CMD ["redis-server", "/usr/local/etc/redis/redis.conf"]