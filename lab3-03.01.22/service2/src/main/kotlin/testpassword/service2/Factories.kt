package testpassword.service2

import org.wildfly.naming.client.WildFlyInitialContextFactory
import java.util.*
import javax.naming.Context
import javax.naming.InitialContext

fun getFromEJBPool(name: String): Any =
    InitialContext(
        Properties().apply {
            put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory::class.java.name) }
    ).lookup(name)