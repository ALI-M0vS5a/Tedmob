package dev.alimoussa.tedmob.data.repository

import dev.alimoussa.tedmob.datastore.PreferencesDataStore
import dev.alimoussa.tedmob.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : UserDataRepository {
    override val userData: Flow<UserData> =
        preferencesDataStore.userData

    override suspend fun updateUsername(username: String) {
        preferencesDataStore.updateUsername(username)
    }

    override suspend fun updatePassword(password: String) {
        preferencesDataStore.updatePassword(password)
    }
}