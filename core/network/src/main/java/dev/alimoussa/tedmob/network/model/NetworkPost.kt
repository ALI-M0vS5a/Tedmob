package dev.alimoussa.tedmob.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkPost(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)
