package org.lenatin.pulse.data

import app.cash.sqldelight.db.SqlDriver
//import kotlin.js.js
//import app.cash.sqldelight.driver.worker.WebWorkerDriver
//import org.w3c.dom.Worker
//import com.vnteam.architecturetemplates.appdatabase.AppDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        TODO("Not yet implemented see: https://sqldelight.github.io/sqldelight/2.1.0/js_sqlite/")
//        return WebWorkerDriver(
//            Worker(
//                js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
//            )
//        ).also { AppDatabase.Schema.awaitCreate(it) }
    }
}