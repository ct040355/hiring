package com.kma.movies.domain.interactor

import com.kma.movies.domain.repository.MoviesRemoteRepository
import com.kma.movies.model.CvResponse
import com.kma.movies.model.JobResponse
import com.kma.movies.model.RegisterStatus
import com.kma.movies.model.UpCV
import io.reactivex.Single

class AllJobByUserUseCase(private val moviesRemoteRepository: MoviesRemoteRepository) {
    fun execute(is_approved: String, username: String):  Single<List<JobResponse>> {
        return moviesRemoteRepository.getAllPostByUser(is_approved, username)
    }

}