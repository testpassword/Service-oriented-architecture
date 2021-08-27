package testpassword.controllers

import testpassword.extensions.*
import testpassword.models.Person
import testpassword.repos.PersonTable
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class PersonServlet: HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            with(req.subpath) {
                when (this) {
                    "find_person_included_in_name" -> (PersonTable `find person included in name` req["name"]).map(Person::transformToJSON) to Res.SC_OK
                    "" -> Person.all().toSet().map(Person::transformToJSON) to Res.SC_OK
                    else -> Person.findById(req.id)!!.transformToJSON() to Res.SC_OK
                }
            }
        }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
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
                else json()("id" to (Person.new {} recoverFromJSON this@with).id) to Res.SC_OK
            }
        }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            Person.findById(req.id)!!.delete()
            json()(
                "msg" to "successfully removed",
                "id" to req.id
            ) to Res.SC_OK
        }
}