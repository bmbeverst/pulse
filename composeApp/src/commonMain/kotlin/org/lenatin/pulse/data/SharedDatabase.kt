package org.lenatin.pulse.data

import org.lenatin.pulse.shared.database.Database

class SharedDatabase(private val databaseDriverFactory: DatabaseDriverFactory, ) {
    private var database: Database? = null

    private suspend fun initDatabase() {
        database.takeIf { it != null } ?: run {
            database = Database(databaseDriverFactory.createDriver())
        }
    }

    suspend operator fun <R> invoke(block: suspend (Database) -> R): R {
        initDatabase()
        return database.takeIf { it != null }?.let {
            block(it)
        } ?: throw IllegalStateException("Database is not initialized")
    }
}