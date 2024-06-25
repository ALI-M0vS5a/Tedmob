package dev.alimoussa.tedmob

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.alimoussa.tedmob.MainActivityUiState.Loading
import dev.alimoussa.tedmob.MainActivityUiState.Success
import dev.alimoussa.tedmob.data.repository.UserDataRepository
import dev.alimoussa.tedmob.model.UserData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    val uiState: StateFlow<MainActivityUiState> =
        userDataRepository.userData.map {
            Success(it)
        }.stateIn(
            scope = viewModelScope,
            initialValue = Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )


    fun updateUsername(email: String) {
        viewModelScope.launch {
            userDataRepository.updateUsername(email)
        }
    }

    fun updatePassword(password: String) {
        viewModelScope.launch {
            userDataRepository.updatePassword(password)
        }
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}