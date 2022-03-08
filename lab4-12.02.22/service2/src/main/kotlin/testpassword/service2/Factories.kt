package testpassword.service2

import org.hibernate.Session
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
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

fun getHttpClient(): Client = ClientBuilder.newBuilder().build()