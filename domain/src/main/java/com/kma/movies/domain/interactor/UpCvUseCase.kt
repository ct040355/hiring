package com.kma.movies.domain.interactor

import com.kma.movies.domain.repository.MoviesRemoteRepository
import com.kma.movies.model.JobResponse
import com.kma.movies.model.RegisterStatus
import com.kma.movies.model.UpCV
import io.reactivex.Single

class UpCvUseCase(private val moviesRemoteRepository: MoviesRemoteRepository) {

    fun execute(data : UpCV): Single<RegisterStatus> {
        return moviesRemoteRepository.upCV(data)
    }

}