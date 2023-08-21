package br.com.kafka.application;

import redis.clients.jedis.Jedis;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;

public class SSLTest
{
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 6380;

    public static void main (String[] args) {

        try {
//            System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
//            System.setProperty("javax.net.ssl.keyStore", "D:\\#Programacao\\projetos\\kafka-springboot\\keystore.p12");
//            System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
//            System.setProperty("javax.net.ssl.trustStoreType", "JKS");
//            System.setProperty("javax.net.ssl.trustStore", "D:\\#Programacao\\projetos\\kafka-springboot\\keystore.jks");
//            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

//            // Configurar o TrustManager para usar a keystore confiança atualizada
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            trustStore.load(null); // Carregar a keystore vazia (será preenchida automaticamente com as keystore confiança padrão)
//
//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(trustStore);
//
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            Jedis jedis = new Jedis(HOSTNAME, PORT, true);
            jedis.connect();
            jedis.auth("redispw");

            System.out.println(jedis.ping());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}