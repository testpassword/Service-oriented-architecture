package service1

import org.springframework.http.converter.HttpMessageNotReadableException
import service1.models.Person
import java.lang.reflect.Field
import kotlin.reflect.KClass

class PaginationError(msg: String): Exception(msg)

fun <T> Iterable<T>.paginate(limit: Int?, offset: Int?): List<T> =
    if (limit == null && offset == null) this.toList()
    else if ((limit == null && offset != null) || (limit != null && offset == null))
        throw PaginationError("You should provide both params: limit and offset and both should be non-negative Int")
    else this.toList().subList(offset!!, this.count()).take(limit!!)

class FilterError(msg: String = "Filters should match '&filters=field:pattern;field:pattern'"): Exception(msg)

fun parseFilters(filters: String): Map<String, String> =
    try {
        if (filters.isBlank()) emptyMap()
        else filters.split(";").map { it.split(":") }.associate { it[0] to it[1] }
    } catch (e: Exception) {
        throw FilterError()
    }

class SortersError(msg: String = "Sorters should match '&sorters=field:dir;field:dir', where dir is ASC or DESC"): Exception(msg)

enum class SORTER { ASC, DESC }

fun parseSorters(sorters: String): Map<String, SORTER> =
    try {
        if (sorters.isBlank()) emptyMap()
        else sorters.split(";").map { it.split(":") }.associate { it[0] to SORTER.valueOf(it[1]) }
    } catch (e: Exception) {
        throw SortersError()
    }

fun <T: Any> parseSliceParams(filters: String, sorters: String, objClass: KClass<T>): Pair<Map<String, String>, Map<String, SORTER>> {
    val f = parseFilters(filters).also { acceptKeys(Person::class, it.keys) }
    val s = parseSorters(sorters).also { acceptKeys(Person::class, it.keys) }
    return f to s
}

fun <T: Any> acceptKeys(objClass: KClass<T>, keys: Set<String>) {
    if (keys.any {
            it !in objClass
                .java
                .declaredFields
                .map(Field::getName)
                .toMutableList()
                .apply { remove("id") }
        }) throw HttpMessageNotReadableException("")
}

inline fun <T, R : Comparable<R>> Iterable<T>.directionalSort(direction: SORTER, crossinline selector: (T) -> R?): List<T> =
    sortedWith(if (direction == SORTER.ASC) compareBy(selector) else compareByDescending(selector))