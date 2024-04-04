package com.kma.movies.domain.interactor

import com.kma.movies.domain.repository.MoviesRemoteRepository
import com.kma.movies.model.JobResponse
import io.reactivex.Single

class SearchJobUseCase(private val moviesRemoteRepository: MoviesRemoteRepository) {

    fun execute(search: String, tag: String, address: String): Single<List<JobResponse>> {
        return moviesRemoteRepository.searchJob(search, tag, address)
    }

}