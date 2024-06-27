package dev.alimoussa.tedmob.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.alimoussa.tedmob.data.repository.UserDataRepository
import dev.alimoussa.tedmob.model.Post
import dev.alimoussa.tedmob.domain.GetPostsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PostsFragmentViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    val uiState: StateFlow<PostsScreenUiState> = flow {
        emit(PostsScreenUiState.Loading)
        try {
            val posts = getPostsUseCase(GetPostsUseCase.Params())
            emit(PostsScreenUiState.Success(posts))
        } catch (e: Exception) {
            emit(PostsScreenUiState.Error(e.localizedMessage ?: "Failed to load posts"))
        }
    }.catch { exception ->
        emit(
            PostsScreenUiState.Error(
                exception.localizedMessage ?: "Unexpected error occurred during data flow."
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PostsScreenUiState.Loading
    )
}


sealed interface PostsScreenUiState {
    data object Loading : PostsScreenUiState
    data class Success(val posts: List<Post>) : PostsScreenUiState
    data class Error(val message: String) : PostsScreenUiState
}