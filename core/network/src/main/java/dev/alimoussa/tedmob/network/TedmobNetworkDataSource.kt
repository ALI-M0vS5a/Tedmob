package dev.alimoussa.tedmob.network

import dev.alimoussa.tedmob.network.model.NetworkPost

/**
 * Interface representing network calls to the backend
 */
interface TedmobNetworkDataSource {
    suspend fun getPots(ids: List<String>? = null): List<NetworkPost>

}