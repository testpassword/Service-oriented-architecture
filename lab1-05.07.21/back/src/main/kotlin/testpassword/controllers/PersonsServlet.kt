package testpassword.controllers

import org.json.JSONException
import testpassword.extensions.*
import testpassword.models.Color
import testpassword.models.Person
import testpassword.repos.PersonTable
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/api/persons/*") class PersonsServlet: HttpServlet() {

    val acceptableKeys = setOf("name", "height", "weight", "passportID", "hairColor")

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            req.`check keys is accepted`(PersonTable.keys)
            with(req.subpath) {
                when (this) {
                    "find_person_included_in_name" -> (PersonTable `find person included in name` req["name"]).map(Person::transformToJSON) to Res.SC_OK
                    "" ->
                        Person
                            .paginate(req.paginationPointer)
                            .let { originals ->
                                var records = originals.toMutableSet()
                                req.filters.forEach { k, v ->
                                    records.retainAll {
                                        when (k) {
                                            "name" -> it.name.contains(v)
                                            "height" -> it.height == v.toInt()
                                            "weight" -> it.weight == v.toInt()
                                            "passportID" -> it.passportID.contains(v)
                                            "hairColor" -> it.hairColor == Color.valueOf(v)
                                            else -> throw NotAcceptedKeyFoundException(setOf(k))
                                        }
                                    }
                                }
                                req.sorters.forEach { k, v ->
                                    records = when (k) {
                                        "name" -> records.sorted(v) { it.name }
                                        "height" -> records.sorted(v) { it.height }
                                        "weight" -> records.sorted(v) { it.weight }
                                        "passportID" -> records.sorted(v) { it.passportID }
                                        "hairColor" -> records.sorted(v) { it.hairColor }
                                        else -> records
                                    }.toMutableSet()
                                }
                                records
                            }
                            .map(Person::transformToJSON) to Res.SC_OK
                    else -> Person.findById(req.id)!!.transformToJSON() to Res.SC_OK
                }
            }
        }

    // по принципу работы это должен быть PATCH, но Jetty его не поддерживает
    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            with(req.json) {
                req `check keys is accepted` PersonTable.keys
                req.`check body completeness`(this)
                json()(
                    "msg" to "successfully modified",
                    "id" to (
                            Person.findById(req.id)!!.apply {
                                try {
                                    name = getString("name")
                                } catch (e: JSONException) {}
                                try {
                                    height = getString("height").toInt()
                                } catch (e: JSONException) {}
                                try {
                                    weight = getString("weight").toInt()
                                } catch (e: JSONException) {}
                                try {
                                    passportID = getString("passportID")
                                } catch (e: JSONException) {}
                                try {
                                    hairColor = Color.valueOf(getString("hairColor"))
                                } catch (e: JSONException) {}
                            }).id
                ) to Res.SC_OK
            }
        }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            with(req.json) {
                req `check keys is accepted` PersonTable.keys
                req.`check body completeness`(this, setOf("name", "height", "weight", "passportID", "hairColor"))
                json()("id" to (
                        Person.new {
                            name = getString("name")
                            height = getInt("height")
                            weight = getInt("weight")
                            passportID = getString("passportID")
                            hairColor = getEnum(Color::class.java, "hairColor")
                        }).id) to Res.SC_OK
            }
        }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            Person.findById(req.id)!!.delete()
            json()("msg" to "successfully removed") to Res.SC_OK
        }
}