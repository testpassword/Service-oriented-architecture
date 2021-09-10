package testpassword

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.required
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHandler
import org.jetbrains.exposed.sql.Database
import testpassword.controllers.AdminServlet
import testpassword.controllers.DragonServlet
import testpassword.controllers.PersonServlet
import testpassword.extensions.Res
import testpassword.extensions.invoke
import testpassword.extensions.json
import kotlin.reflect.KClass

val GLOBAL_EXCEPTION_HANDLERS = emptyMap<KClass<out Exception>, Pair<Any, Int>>().toMutableMap()
val SERVLET_MAPPING = mapOf(
    AdminServlet::class to "/api/admin/*",
    DragonServlet::class to "/api/dragons/*",
    PersonServlet::class to "/api/persons/*"
)

infix operator fun String.minus(removable: String): String = this.replace(removable, "")

fun initDB(conf: String) {
    val creds = (conf.indexOf("//") + 2 to conf.indexOf("@") - 1).let { conf.substring(it.first..it.second) }
    val (user, pass) = creds.split(":")
    val url = conf - creds - "@"
    Database.connect(url, "org.postgresql.Driver", user, pass)
}

fun initServer(port: Int) =
    Server(port).apply {
        handler = ServletHandler().apply {
            SERVLET_MAPPING.forEach { k, v -> addServletWithMapping(k.java, v) }
        }
    }.start()

fun initArgs(args: Array<String>): Pair<Int, String> {
    val parser = ArgParser("soa1")
    val port by parser.option(ArgType.Int, "port", "P", "port which server use for webapp").default(8090)
    val dbConfig by parser.option(ArgType.String, "database", "DB",
        "database config in format: jdbc:postgresql://user:password@netloc:port/dbname?param1=value1&...").required()
    parser.parse(args)
    return port to dbConfig
}

fun main(args: Array<String>) {
    val (port, dbConfig) = initArgs(args)
    GLOBAL_EXCEPTION_HANDLERS[NullPointerException::class] = json()("msg" to "required entity didn't exists") to Res.SC_NOT_FOUND
    initDB(dbConfig)
    initServer(port)
}