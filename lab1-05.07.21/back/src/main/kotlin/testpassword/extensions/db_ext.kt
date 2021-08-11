package testpassword.extensions

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder

fun <T: LongEntity> LongEntityClass<T>.getByIds(vararg ids: Number): Set<T> = ids.mapNotNull { this.findById(it.toLong()) }.toSet()

fun <T: LongEntity> LongEntityClass<T>.removeByIds(vararg ids: Number) = getByIds(*ids).forEach(LongEntity::delete)

// TODO: сделать через дженерики
val isPositiveInt: SqlExpressionBuilder.(Column<Int>) -> Op<Boolean> = { it greater 0 }
val isPositiveDouble: SqlExpressionBuilder.(Column<Double>) -> Op<Boolean> = { it greater 0 }
val isNotBlank: SqlExpressionBuilder.(Column<String>) -> Op<Boolean> = { it neq "" }