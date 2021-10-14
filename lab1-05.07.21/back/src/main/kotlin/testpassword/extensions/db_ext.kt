package testpassword.extensions

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder

fun <T: LongEntity> LongEntityClass<T>.getByIds(vararg ids: Number): Set<T> = ids.mapNotNull { this.findById(it.toLong()) }.toSet()

fun <T: LongEntity> LongEntityClass<T>.removeByIds(vararg ids: Number) = getByIds(*ids).forEach(LongEntity::delete)

// TODO: сделать через дженерики
val isPositiveInt: SqlExpressionBuilder.(Column<Int>) -> Op<Boolean> = { it greater 0 }
val isPositiveDouble: SqlExpressionBuilder.(Column<Double>) -> Op<Boolean> = { it greater 0 }
val isNotBlank: SqlExpressionBuilder.(Column<String>) -> Op<Boolean> = { it neq "" }

infix fun <T: LongEntity> LongEntityClass<T>.paginate(pointer: Pair<Int, Long>?): Set<T> =
    (this.all().let { if (pointer != null) it.limit(pointer.first, pointer.second) else it }).toSet()

val LongIdTable.keys: Set<String>
    get() = this.columns.map { it.name }.filter { it != "id" }.toSet()

inline fun <T, R : Comparable<R>> Iterable<T>.sorted(direction: SORTER, crossinline selector: (T) -> R?): List<T> =
    sortedWith(if (direction == SORTER.ASC) compareBy(selector) else compareByDescending(selector))