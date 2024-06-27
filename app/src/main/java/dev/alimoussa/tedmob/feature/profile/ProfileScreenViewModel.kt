package dev.alimoussa.tedmob.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.alimoussa.tedmob.data.repository.UserDataRepository
import dev.alimoussa.tedmob.domain.LogoutUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    val profileUiState: StateFlow<ProfileScreenState> =
        userDataRepository.userData.map {
            ProfileScreenState.Success(
                email = it.email
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = ProfileScreenState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.execute()
        }
    }
}


sealed interface ProfileScreenState {
    data object Loading : ProfileScreenState
    data class Success(val email: String) : ProfileScreenState
}