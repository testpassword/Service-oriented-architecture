package testpassword.extensions

import org.jetbrains.exposed.sql.transactions.transaction
import org.json.JSONObject
import testpassword.StartupParamsHolder.GLOBAL_EXCEPTION_HANDLERS
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.KClass

typealias json = JSONObject
operator fun json.invoke(vararg data: Pair<String, Any>) = this.apply { data.forEach { this.put(it.first, it.second) } }

typealias Res = HttpServletResponse
operator fun Res.invoke(customExceptionHandlers: Map<KClass<out Exception>, Pair<Any, Int>> = emptyMap(),
                        func: () -> Pair<Any?, Int>): Unit =
    transaction {
        val (data, code) = try {
            func()
        } catch (e: Exception) {
            (customExceptionHandlers + GLOBAL_EXCEPTION_HANDLERS)[e::class]
                ?.let { it.first.toString() to it.second }
                ?: (json()("error" to e.localizedMessage) to Res.SC_BAD_REQUEST)
        }
        addHeader("Access-Control-Allow-Origin", "*")
        status = code
        contentType = "application/json"
        writer.println(data)
    }