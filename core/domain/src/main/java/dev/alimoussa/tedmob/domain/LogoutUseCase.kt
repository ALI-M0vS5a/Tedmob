package dev.alimoussa.tedmob.domain

import dev.alimoussa.tedmob.data.repository.UserDataRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository
) {
    suspend fun execute() {
        userDataRepository.updatePassword("")
        userDataRepository.updateUsername("")
    }
}