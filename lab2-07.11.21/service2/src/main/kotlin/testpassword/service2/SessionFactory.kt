package testpassword.service2

import org.hibernate.Session
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import java.io.FileInputStream
import java.security.KeyStore
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder

fun getDBSession(): Session =
    MetadataSources(
        StandardServiceRegistryBuilder()
            .configure()
            .build()
    )
        .buildMetadata()
        .buildSessionFactory()
        .openSession()

fun getHttpClient(): Client =
    ClientBuilder
        .newBuilder()
        .trustStore(
            KeyStore
                .getInstance(KeyStore.getDefaultType())
                .apply {
                    load(FileInputStream(System.getProperty("ssl_cert")), System.getProperty("ssl_pass").toCharArray())
                }
        )
        .hostnameVerifier { _, _ -> true }
        .build()