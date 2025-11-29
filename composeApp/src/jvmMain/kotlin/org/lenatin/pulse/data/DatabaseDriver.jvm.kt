package org.lenatin.pulse.data

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.lenatin.pulse.shared.database.Database
import java.io.File

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        val isDebug = true// or you can use BuildKonfig.isDebug to setup your logic
        val parentFolder = if (isDebug) {
            File(System.getProperty("java.io.tmpdir"))
        } else {
            File(System.getProperty("user.home") + "/MyFancyApp")
        }
        if (!parentFolder.exists()) {
            parentFolder.mkdirs()
        }
        val databasePath = if (isDebug) {
            File(System.getProperty("java.io.tmpdir"), DB_NAME)
        } else {
            File(parentFolder, DB_NAME)
        }
        return JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}").also { driver ->
            Database.Schema.create(driver = driver)
        }
    }

}