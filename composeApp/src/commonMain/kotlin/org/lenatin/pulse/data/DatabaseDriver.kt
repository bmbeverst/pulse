package org.lenatin.pulse.data

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

expect class DatabaseDriverFactory {

     suspend fun createDriver(): SqlDriver
}
const val DB_NAME = "test.db"
