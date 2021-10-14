package testpassword.controllers

import testpassword.NotAcceptedKeyFoundException
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
            req.checkKeysIsAccepted(PersonTable.keys)
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
            req.checkKeysIsAccepted(PersonTable.keys)
            with(req.json) {
                if (isEmpty) "Nothing to modify" to Res.SC_ACCEPTED
                else json()(
                    "msg" to "successfully modified",
                    "id" to (Person.findById(req.id)!! recoverFromJSON this@with).id
                ) to Res.SC_OK
            }
        }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            with(req.json) {
                if (isEmpty) "You should provide info for creation" to Res.SC_BAD_REQUEST
                else jsonP { "id" to (Person.new {} recoverFromJSON this@with).id } to Res.SC_OK
            }
        }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            Person.findById(req.id)!!.delete()
            jsonP { "msg" to "successfully removed" } to Res.SC_OK
        }
}