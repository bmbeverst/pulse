package org.lenatin.pulse.data

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
const val DB_NAME = "test.db"
