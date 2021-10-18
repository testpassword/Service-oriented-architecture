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

class NotAcceptedKeyFoundException(keys: Set<String>): Exception("Not acceptable keys is ${keys.joinToString(", ")}")

infix fun Req.`check keys is accepted`(entityKeys: Set<String>) =
    (this.filters + this.sorters)
        .keys
        .filter { it !in entityKeys }
        .toSet()
        .also { if (it.isNotEmpty()) throw NotAcceptedKeyFoundException(it) }

fun Req.`check body completeness`(jsonObject: JSONObject, requiredKeys: Set<String> = emptySet()) {
    if (jsonObject.isEmpty) throw Exception("Body is empty")
    (requiredKeys - jsonObject.keySet()).also { if (it.isNotEmpty()) throw Exception("Missing keys: [${it.joinToString(",")}]") }
}

class PaginationError(msg: String): Exception(msg)

val Req.paginationPointer: Pair<Int, Long>?
    get() {
        val limit = this["limit"]
        val offset = this["offset"]
        return if (limit.isBlank() && offset.isBlank()) null
        else try {
            (limit.toInt() to offset.toLong()).also { if (it.first < 0 || it.second < 0) throw NumberFormatException() }
        } catch (e: Exception) {
            throw PaginationError(when (e) {
                is NumberFormatException -> "You should provide both params: limit and offset and both should be non-negative Int"
                else -> "Unexpected exception"
            })
        }
    }

class FilterError(msg: String = "Filters should match '&filters=field:pattern;field:pattern'"): Exception(msg)

val Req.filters: Map<String, String>
    get() {
        val filters = this["filters"]
        return if (filters.isBlank()) emptyMap()
        else try {
            this["filters"].split(";").map { it.split(":") }.associate { it[0] to it[1] }
        } catch (e: Exception) { throw FilterError() }
    }

class SortersError(msg: String = "Sorters should match '&sorters=field:dir;field:dir', where dir is ASC or DESC"): Exception(msg)

enum class SORTER { ASC, DESC }

val Req.sorters: Map<String, SORTER>
    get() {
        val sorters = this["sorters"]
        return if (sorters.isBlank()) emptyMap()
        else try {
            this["sorters"].split(";").map { it.split(":") }.associate { it[0] to SORTER.valueOf(it[1]) }
        } catch (e: Exception) { throw SortersError() }
    }