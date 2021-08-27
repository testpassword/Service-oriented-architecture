package testpassword.extensions

import org.jetbrains.exposed.sql.transactions.transaction
import org.json.JSONObject
import testpassword.GLOBAL_EXCEPTION_HANDLERS
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.KClass

typealias Res = HttpServletResponse

typealias json = JSONObject

operator fun JSONObject.invoke(vararg data: Pair<String, Any>) = this.apply { data.forEach { this.put(it.first, it.second) } }

operator fun Res.invoke(customExceptionHandlers: Map<KClass<out Exception>, Pair<Any, Int>> = emptyMap(),
                        func: () -> Pair<Any?, Int>) {
    transaction {
        val (data, code) = try {
            func()
        } catch (e: Exception) {
            println(e.stackTraceToString())
            (customExceptionHandlers + GLOBAL_EXCEPTION_HANDLERS)[e::class]
                ?.let { it.first.toString() to it.second }
                ?: (e.toString() to Res.SC_BAD_REQUEST)
        }
        status = code
        contentType = "application/json"
        println(data)
        writer.println(data)
    }
    // TODO: { "error": errText }
}
