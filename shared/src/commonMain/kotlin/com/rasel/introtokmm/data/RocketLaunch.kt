package com.rasel.introtokmm.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketLaunch(
    val id: String,
    @SerialName("flight_number")
    val flightNumber: Int,
    val name: String,
    @SerialName("date_utc")
    val dateUTC: String,
    val details: String?,
    val success: Boolean?
)

@Serializable
data class LaunchDetail(
    val id: String,
    @SerialName("flight_number")
    val flightNumber: Int,
    val name: String,
    @SerialName("date_utc")
    val dateUTC: String,
    val details: String?,
    val success: Boolean?,
    val capsules: List<String>,
    val crew: List<String>,
    val failures: List<Failure>,
    val links: Links,
    val payloads: List<String>,
    val rocket: String?,
    val ships: List<String>
)

@Serializable
data class Failure(
    val altitude: Int?,
    val reason: String?,
    val time: Int?
)

@Serializable
data class Links(
    val article: String,
    val flickr: Flickr,
    val patch: Patch,
    val presskit: String?,
    val reddit: Reddit,
    val webcast: String?,
    val wikipedia: String?
)

@Serializable
data class Flickr(
    val original: List<String>,
    val small: List<String>
)

@Serializable
data class Patch(
    val large: String?,
    val small: String?
)

@Serializable
data class Reddit(
    val campaign: String?,
    val launch: String?,
    val media: String?,
    val recovery: String?
)
