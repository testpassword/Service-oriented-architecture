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
                with(req.tokens.last()) {
                    when (this) {
                        "find_person_included_in_name" -> PersonTable `find person included in name` req["name"] to Res.SC_OK
                        "" -> Person.all() to Res.SC_OK
                        else -> Person.findById(req.id) to Res.SC_OK
                    }
                }
            }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) =
            resp {
                with(req.json) {
                    if (this.isEmpty) "Nothing to modify" to Res.SC_ACCEPTED
                    else "${(Person.findById(req.id)!! recoverFromJSON this).id} successfully modified" to Res.SC_OK
                }
            }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) =
            resp {
                with(req.json) {
                    if (this.isEmpty) "You should provide info for creation" to Res.SC_BAD_REQUEST
                    else (Person.new {} recoverFromJSON this).id to Res.SC_OK
                }
            }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) =
            resp {
                Person.findById(req.id)!!.delete()
                "${Person::class.java.name} with id = ${req.id} successfully removed" to Res.SC_OK
            }
}