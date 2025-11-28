package org.lenatin.pulse.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.lenatin.pulse.shared.database.Database

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(
        schema = Database.Schema,
        name = DB_NAME
    )
}
