package testpassword.controllers

import testpassword.extensions.*
import testpassword.models.Dragon
import testpassword.models.Person
import testpassword.repos.DragonTable
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

// TODO: докер
// TODO: JSONSchemeValidator
// TODO: если параметр запроса будет отсутсвовать - будет null, если пользователя не будет - будет null, но обработаются они одинаково, что плохо

class DragonServlet: HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            with(req.tokens.last()) {
                when (this) {
                    "grouped_by_type" -> DragonTable.groupByType() to Res.SC_OK
                    "with_killer_weaker_then" -> DragonTable `find with killer weaker then` Person.findById(req["killer_id"].toLong())!! to Res.SC_OK
                    "" -> Dragon.all() to Res.SC_OK
                    else -> Dragon.findById(req.id) to Res.SC_OK
                }
            }
        }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) =
            resp {
                with(req.json) {
                    if (this.isEmpty) "Nothing to modify" to Res.SC_ACCEPTED
                    else "${(Dragon.findById(req.id)!! recoverFromJSON this@with).id} successfully modified" to Res.SC_OK
                }
            }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) =
            resp {
                with(req.json) {
                    if (this.isEmpty) "You should provide info for creation" to Res.SC_BAD_REQUEST
                    else (Dragon.new {} recoverFromJSON this@with).id to Res.SC_OK
                }
            }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) =
            resp {
                Dragon.findById(req.id)!!.delete()
                "${Dragon::class.java.name} with id = ${req.id} successfully removed" to Res.SC_OK
            }
}