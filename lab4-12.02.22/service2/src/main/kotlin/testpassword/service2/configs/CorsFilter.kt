package testpassword.service2.configs

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.ext.Provider

@Provider class CorsFilter: ContainerResponseFilter {

    override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) =
        listOf(
            "Access-Control-Allow-Origin" to "*",
            "Access-Control-Allow-Headers" to "*",
            "Access-Control-Allow-Methods" to "GET, POST, PUT, DELETE, OPTIONS, HEAD"
        ).forEach { responseContext.headers.add(it.first, it.second) }
}