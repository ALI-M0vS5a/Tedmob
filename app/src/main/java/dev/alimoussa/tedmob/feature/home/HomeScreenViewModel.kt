package dev.alimoussa.tedmob.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.alimoussa.tedmob.data.repository.UserDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    userDataRepository: UserDataRepository
) : ViewModel() {
    val uiState: StateFlow<HomeScreenState> =
        userDataRepository.userData.map {
            HomeScreenState.Success(
                email = it.email
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = HomeScreenState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )
}


sealed interface HomeScreenState {
    data object Loading : HomeScreenState
    data class Success(val email: String) : HomeScreenState
}