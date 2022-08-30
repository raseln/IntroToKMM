package com.rasel.introtokmm.shared.cache

import com.rasel.introtokmm.data.RocketLaunch

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllLaunches()
        }
    }

    internal fun getAllLaunches(): List<RocketLaunch> {
        return dbQuery.selectAllLaunches(::mapLaunchSelecting).executeAsList()
    }

    private fun mapLaunchSelecting(
        id: String,
        flightNumber: Long,
        name: String,
        details: String?,
        success: Boolean?,
        dateUTC: String
    ): RocketLaunch {
        return RocketLaunch(
            id = id,
            flightNumber = flightNumber.toInt(),
            name = name,
            details = details,
            dateUTC = dateUTC,
            success = success
        )
    }

    internal fun createLaunches(launches: List<RocketLaunch>) {
        dbQuery.transaction {
            launches.forEach { launch ->
                insertLaunch(launch)
            }
        }
    }

    private fun insertLaunch(launch: RocketLaunch) {
        dbQuery.insertLaunch(
            id = launch.id,
            flightNumber = launch.flightNumber.toLong(),
            name = launch.name,
            details = launch.details,
            success = launch.success,
            dateUTC = launch.dateUTC
        )
    }
}
