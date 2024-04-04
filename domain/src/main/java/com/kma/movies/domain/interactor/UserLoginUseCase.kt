package com.kma.movies.domain.interactor

import com.kma.movies.domain.repository.MoviesRemoteRepository
import com.kma.movies.model.LoginResponse
import com.kma.movies.model.RegisterStatus
import com.kma.movies.model.UserLogin
import io.reactivex.Single

class UserLoginUseCase (private val moviesRemoteRepository: MoviesRemoteRepository) {
    fun execute(userLogin: UserLogin) : Single<RegisterStatus> {
        return moviesRemoteRepository.loginUser(userLogin)
    }
}