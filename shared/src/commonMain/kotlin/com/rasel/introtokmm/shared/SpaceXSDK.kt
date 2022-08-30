package com.rasel.introtokmm.shared

import com.rasel.introtokmm.data.LaunchDetail
import com.rasel.introtokmm.data.RocketLaunch
import com.rasel.introtokmm.shared.cache.Database
import com.rasel.introtokmm.shared.cache.DatabaseDriverFactory
import com.rasel.introtokmm.shared.network.SpaceXApi

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }

    @Throws(Exception::class)
    suspend fun getLaunchById(id: String): LaunchDetail {
        return api.getLaunchById(id)
    }
}