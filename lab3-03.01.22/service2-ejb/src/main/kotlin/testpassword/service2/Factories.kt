package testpassword.service2

import org.hibernate.Session
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.json.JSONObject
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType

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

fun getServices(): List<JSONObject> =
    JSONObject(
        getHttpClient()
            .target("${System.getProperty("consul_url")}/v1/agent/services")
            .request(MediaType.APPLICATION_JSON)
            .get(object: GenericType<String>(){})
    ).let {
        it.keySet().map { k -> it.getJSONObject(k) }
    }