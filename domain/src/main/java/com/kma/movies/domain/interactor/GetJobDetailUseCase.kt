package com.kma.movies.domain.interactor

import com.kma.movies.domain.repository.MoviesRemoteRepository
import com.kma.movies.model.JobResponse
import io.reactivex.Single

class GetJobDetailUseCase(private val moviesRemoteRepository: MoviesRemoteRepository) {

    fun execute(id: String): Single<List<JobResponse>> {
        return moviesRemoteRepository.getJobById(id)
    }

}