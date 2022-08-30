package com.rasel.introtokmm.shared.network

import com.rasel.introtokmm.data.LaunchDetail
import com.rasel.introtokmm.data.RocketLaunch
import com.rasel.introtokmm.utils.toFormattedString
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json

class SpaceXApi {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun getAllLaunches(): List<RocketLaunch> {
        val response : List<RocketLaunch> = httpClient.get("https://api.spacexdata.com/v4/launches").body()
        val list = mutableListOf<RocketLaunch>()
        response.forEach { item ->
            val formattedDate = Instant.parse(item.dateUTC).toLocalDateTime(TimeZone.currentSystemDefault()).toFormattedString()
            list.add(
                RocketLaunch(
                    id = item.id,
                    flightNumber = item.flightNumber,
                    name = item.name,
                    dateUTC = formattedDate,
                    details = item.details ?: "Not Available",
                    success = item.success
                )
            )
        }
        return list
    }

    suspend fun getLaunchById(id: String): LaunchDetail {
        val launchDetail: LaunchDetail = httpClient.get("https://api.spacexdata.com/v4/launches/$id").body()
        val date = Instant.parse(launchDetail.dateUTC).toLocalDateTime(TimeZone.currentSystemDefault()).toFormattedString()
        return launchDetail.copy(dateUTC = date)
    }
}