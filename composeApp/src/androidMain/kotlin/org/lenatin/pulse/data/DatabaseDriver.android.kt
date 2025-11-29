package org.lenatin.pulse.data

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.lenatin.pulse.shared.database.Database

actual class DatabaseDriverFactory(private val context: Context) {

    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema.synchronous(), context, DB_NAME)
    }
}
