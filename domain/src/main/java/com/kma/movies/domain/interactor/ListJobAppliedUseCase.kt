package com.kma.movies.domain.interactor

import com.kma.movies.domain.repository.MoviesRemoteRepository
import com.kma.movies.model.JobResponse
import com.kma.movies.model.Movie
import io.reactivex.Single

class ListJobAppliedUseCase(private val moviesLocalRepository: MoviesRemoteRepository) {

    fun execute(data : String): Single<List<JobResponse>> {
        return moviesLocalRepository.getJobApplied(data)
    }

}