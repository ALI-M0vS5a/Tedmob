package dev.alimoussa.tedmob.data.repository

import dev.alimoussa.tedmob.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>
    suspend fun updateUsername(username: String)
    suspend fun updatePassword(password: String)
}