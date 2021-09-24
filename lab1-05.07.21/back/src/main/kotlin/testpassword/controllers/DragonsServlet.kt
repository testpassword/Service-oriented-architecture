package testpassword.controllers

import testpassword.extensions.*
import testpassword.models.Dragon
import testpassword.models.Person
import testpassword.repos.DragonTable
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
                    "" -> Dragon.all().toSet().map(Dragon::transformToJSON) to Res.SC_OK
                    else -> Dragon.findById(req.id)!!.transformToJSON() to Res.SC_OK
                }
            }
        }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            with(req.json) {
                if (this.isEmpty) "Nothing to modify" to Res.SC_ACCEPTED
                else json()(
                    "msg" to "successfully modified",
                    "id" to (Dragon.findById(req.id)!! recoverFromJSON this@with).id
                ) to Res.SC_OK
            }
        }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            with(req.json) {
                if (isEmpty) "You should provide info for creation" to Res.SC_BAD_REQUEST
                else json()("id" to (Dragon.new {} recoverFromJSON this@with).id) to Res.SC_OK
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