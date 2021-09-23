package testpassword.controllers

import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener
import testpassword.StartupParamsHolder.initDB
import testpassword.StartupParamsHolder.initGlobalExceptions

@WebListener class WARInitializer: ServletContextListener {

    override fun contextInitialized(sce: ServletContextEvent) {
        initDB(System.getProperty("database"))
        initGlobalExceptions()
    }

    override fun contextDestroyed(p0: ServletContextEvent) { /* we just should override this */ }
}