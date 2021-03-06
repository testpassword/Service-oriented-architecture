package testpassword.controllers

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.sql.SchemaUtils
import testpassword.models.Coordinates
import testpassword.models.Dragon
import testpassword.models.Person
import testpassword.extensions.*
import testpassword.repos.CoordinatesTable
import testpassword.repos.DragonTable
import testpassword.repos.PersonTable
import javax.servlet.annotation.WebServlet
import javax.servlet.http.*

@WebServlet("/api/admin/*") class AdminServlet: HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) =
        with(response) {
            contentType = "text/html"
            writer.println("""
            <html>
                <body>
                    <h1>WELCOME, GOD OF DIGITAL WORLD</h1>
                </body>    
            </html>
            """.trimIndent())
        }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            with(SchemaUtils) {
                create(CoordinatesTable, PersonTable)
                // we separate creation of DragonTable because other should be already created otherwise it's throws an exception
                create(DragonTable)
            }
            json()("msg" to "done creation tables") to Res.SC_OK
        }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            sequenceOf(Dragon, Person, Coordinates).flatMap { it.all() }.forEach(LongEntity::delete)
            json()("msg" to "database cleared") to Res.SC_OK
        }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) =
        resp {
            TODO("put your code for testing purpose")
        }
}