package com.kma.movies.domain.interactor

import com.kma.movies.domain.repository.MoviesRemoteRepository
import com.kma.movies.model.Job
import com.kma.movies.model.JobResponse
import com.kma.movies.model.RegisterStatus
import com.kma.movies.model.UpCV
import io.reactivex.Single

class PostJobUseCase(private val moviesRemoteRepository: MoviesRemoteRepository) {

    fun execute(job : Job): Single<RegisterStatus> {
        return moviesRemoteRepository.postJob(job)
    }

}