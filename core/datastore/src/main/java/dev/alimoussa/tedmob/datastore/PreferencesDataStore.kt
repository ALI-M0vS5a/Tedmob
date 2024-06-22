package dev.alimoussa.tedmob.datastore


import androidx.datastore.core.DataStore
import dev.alimoussa.tedmob.model.UserData
import kotlinx.coroutines.flow.map
import tedmob.UserPreferences
import tedmob.copy
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData = userPreferences.data.map {
        UserData(
            email = it.email,
            password = it.password
        )
    }

    suspend fun updateUsername(username: String) {
        userPreferences.updateData {
            it.copy {
                this.email = username
            }
        }
    }

    suspend fun updatePassword(password: String) {
        userPreferences.updateData {
            it.copy {
                this.password = password
            }
        }
    }
}