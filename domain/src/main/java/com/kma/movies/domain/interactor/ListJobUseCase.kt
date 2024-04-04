package com.kma.movies.domain.interactor

import com.kma.movies.domain.repository.MoviesRemoteRepository
import com.kma.movies.model.JobResponse
import com.kma.movies.model.Movie
import io.reactivex.Single

class ListJobUseCase(private val moviesLocalRepository: MoviesRemoteRepository) {

    fun execute(): Single<List<JobResponse>> {
        return moviesLocalRepository.getAllJob()
    }

}