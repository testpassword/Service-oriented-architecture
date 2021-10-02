package testpassword

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHandler
import org.jetbrains.exposed.sql.Database
import org.json.JSONArray
import testpassword.controllers.AdminServlet
import testpassword.controllers.DragonsServlet
import testpassword.controllers.PersonsServlet
import testpassword.extensions.Res
import kotlin.reflect.KClass

object StartupParamsHolder {

    val GLOBAL_EXCEPTION_HANDLERS = emptyMap<KClass<out Exception>, Pair<Any, Int>>().toMutableMap()

    fun initDB(conf: String) {
        val creds = (conf.indexOf("//") + 2 to conf.indexOf("@") - 1).let { conf.substring(it.first..it.second) }
        val (user, pass) = creds.split(":")
        val url = conf - creds - "@"
        Database.connect(url, "org.postgresql.Driver", user, pass)
    }

    fun initGlobalExceptions() {
        GLOBAL_EXCEPTION_HANDLERS[NullPointerException::class] = JSONArray() to Res.SC_OK
    }

    fun initEmbeddedServer(port: Int) =
        Server(port).apply {
            handler = ServletHandler().apply {
                mapOf(
                    AdminServlet::class to "/api/admin/*",
                    DragonsServlet::class to "/api/dragons/*",
                    PersonsServlet::class to "/api/persons/*"
                ).forEach { k, v -> addServletWithMapping(k.java, v) }
            }
        }.start()
}