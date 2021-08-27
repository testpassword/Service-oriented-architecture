package testpassword.extensions

import org.json.JSONObject
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest

typealias Req = HttpServletRequest

infix operator fun Req.get(param: String): String = this.getParameter(param) ?: ""

infix operator fun Req.contains(param: String): Boolean = this.tokens.contains(param)

val Req.json: JSONObject get() = JSONObject(this.reader.lines().collect(Collectors.joining(System.lineSeparator())))

val Req.tokens: List<String> get() = this.pathInfo?.split("/")?.toList() ?: emptyList()

val Req.id: Long get() = this.tokens.last().toLongOrNull() ?: 0

val Req.subpath: String get() = this.tokens.lastOrNull() ?: ""