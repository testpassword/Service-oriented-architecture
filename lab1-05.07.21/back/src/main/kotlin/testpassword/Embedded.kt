package testpassword

import testpassword.StartupParamsHolder.initDB
import testpassword.StartupParamsHolder.initGlobalExceptions
import testpassword.StartupParamsHolder.initEmbeddedServer

infix operator fun String.minus(removable: String): String = this.replace(removable, "")

fun main() {
    initDB(System.getenv("DATABASE"))
    initGlobalExceptions()
    initEmbeddedServer(System.getenv("PORT").toIntOrNull() ?: 8080)
}