package testpassword.controllers

import org.json.JSONException
import testpassword.extensions.*
import testpassword.models.Color
import testpassword.models.Coordinates
import testpassword.models.Dragon
import testpassword.models.Person
import testpassword.repos.DragonTable
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/api/dragons/*") class DragonsServlet: HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            with(req.subpath) {
                when (this) {
                    "grouped_by_type" -> json()(*DragonTable.groupByType().map { it.key.toString() to it.value }.toTypedArray()) to Res.SC_OK
                    "find_with_killer_weaker_then" -> DragonTable `find with killer weaker then` Person.findById(req["killer_id"].toLong())!! to Res.SC_OK
                    "" ->
                        Dragon
                            .paginate(req.paginationPointer)
                            .let { originals ->
                                var records = originals.toMutableSet()
                                req.filters.forEach { k, v ->
                                    records.retainAll {
                                        when (k) {
                                            "name" -> it.name.contains(v)
                                            "coordinates" -> it.coordinates == Coordinates.findById(v.toLong())
                                            "creationDate" -> it.creationDate.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli() == Date(v.toLong()).time
                                            "age" -> it.age == v.toInt()
                                            "wingspan" -> it.wingspan == v.toDouble()
                                            "color" -> it.color == Color.valueOf(v)
                                            "type" -> it.type == Dragon.DragonType.valueOf(v)
                                            "killerID" -> it.killer == Person.findById(v.toLong())
                                            else -> throw NotAcceptedKeyFoundException(setOf(k))
                                        }
                                    }
                                }
                                req.sorters.forEach { k, v ->
                                    records = when (k) {
                                        "name" -> records.sorted(v) { it.name }
                                        "coordinates" -> records.sorted(v) { it.coordinates }
                                        "creationDate" -> records.sorted(v) { it.creationDate }
                                        "age" -> records.sorted(v) { it.age }
                                        "wingspan" -> records.sorted(v) { it.wingspan }
                                        "color" -> records.sorted(v) { it.color }
                                        "type" -> records.sorted(v) { it.type }
                                        "killerID" -> records.sorted(v) { it.killer }
                                        else -> records
                                    }.toMutableSet()
                                }
                                records
                            }
                            .map(Dragon::transformToJSON) to Res.SC_OK
                    else -> Dragon.findById(req.id)!!.transformToJSON() to Res.SC_OK
                }
            }
        }

    // по принципу работы это должен быть PATCH, но Jetty его не поддерживает
    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            with(req.json) {
                req `check keys is accepted` keySet()
                req.`check body completeness`(this)
                json()(
                    "msg" to "successfully modified",
                    "id" to (Dragon.findById(req.id)!!.apply {
                        try {
                            name = getString("name")
                        } catch (e: JSONException) {}
                        try {
                            val coords = getJSONObject("coordinates")
                            coordinates = Coordinates.new {
                                x = coords.getDouble("x")
                                y = coords.getDouble("y")
                            }
                        } catch (e: JSONException) {}
                        try {
                            creationDate = LocalDateTime.now()
                        } catch (e: JSONException) {}
                        try {
                            age = getString("age").toInt()
                        } catch (e: JSONException) {}
                        try {
                            wingspan = getString("wingspan").toDouble()
                        } catch (e: JSONException) {}
                        try {
                            color = Color.valueOf(getString("color"))
                        } catch (e: JSONException) {}
                        try {
                            type = Dragon.DragonType.valueOf(getString("type"))
                        } catch (e: JSONException) {}
                        try {
                            killer = Person.findById(getString("killerID").toLong())
                        } catch (e: JSONException) {}
                    }).id
                ) to Res.SC_OK
            }
        }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            with(req.json) {
                req `check keys is accepted` keySet()
                req.`check body completeness`(this, setOf("name", "age", "wingspan", "color"))
                json()("id" to (Dragon.new {
                    name = getString("name")
                    try {
                        val coords = getJSONObject("coordinates")
                        coordinates = Coordinates.new {
                            x = coords.getDouble("x")
                            y = coords.getDouble("y")
                        }
                    } catch (e: JSONException) {}
                    creationDate = LocalDateTime.now()
                    age = getInt("age")
                    wingspan = getDouble("wingspan")
                    color = getEnum(Color::class.java, "color")
                    try {
                        type = getEnum(Dragon.DragonType::class.java, "type")
                    } catch (e: JSONException) {}
                    try {
                        killer = Person.findById(getLong("killerID"))
                    } catch (e: JSONException) {}
                }).id) to Res.SC_OK
            }
        }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            Dragon.findById(req.id)!!.delete()
            json()(
                "msg" to "successfully removed",
                "id" to req.id
            ) to Res.SC_OK
        }
}