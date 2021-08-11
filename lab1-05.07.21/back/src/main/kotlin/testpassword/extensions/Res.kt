package testpassword.extensions

import com.beust.klaxon.Klaxon
import org.jetbrains.exposed.sql.transactions.transaction
import javax.servlet.http.HttpServletResponse
import kotlin.reflect.KClass

typealias Res = HttpServletResponse

operator fun Res.invoke(customExceptionHandlers: Map<KClass<out Exception>, Pair<String, Int>> = emptyMap(),
                        toJson: Boolean = true,
                        isTransactional: Boolean = true,
                        func: () -> Pair<Any?, Int>) {
    val (data, status) = try {
        if (isTransactional) transaction { func() } else func()
    } catch (e: Exception) {
        customExceptionHandlers[e::class]?.let { it.first to it.second } ?: (e.toString() to Res.SC_BAD_REQUEST)
    // TODO: проверять также globalException
    }
    this.status = status
    (if (toJson) "application/json" to Klaxon().toJsonString(data) else "text/plain" to data).apply {
        contentType = first
        writer.println(second)
    }
}