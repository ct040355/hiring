package com.kma.movies.domain.repository

import com.kma.movies.model.CheckFavouriteResponse
import com.kma.movies.model.CvResponse
import com.kma.movies.model.DataUserResponse
import com.kma.movies.model.DeleteFavouriteResponse
import com.kma.movies.model.FavouriteResponse
import com.kma.movies.model.GetCommentResponse
import com.kma.movies.model.Job
import com.kma.movies.model.JobResponse
import com.kma.movies.model.Movie
import com.kma.movies.model.MovieDetail
import com.kma.movies.model.MoviesResponse
import com.kma.movies.model.PostComment
import com.kma.movies.model.PostCommentResponse
import com.kma.movies.model.RegisterStatus
import com.kma.movies.model.SearchMovies
import com.kma.movies.model.Status
import com.kma.movies.model.UpCV
import com.kma.movies.model.UserLogin
import com.kma.movies.model.UserRegister
import io.reactivex.Single

interface MoviesRemoteRepository {

    fun getPopularMovies(page: Int): Single<MoviesResponse>

    fun getUpcomingMovies(page: Int): Single<MoviesResponse>

    fun getSingleMovie(id: String): Single<MovieDetail>
    fun registerUser(userRegister: UserRegister): Single<RegisterStatus>
    fun loginUser(userLogin: UserLogin): Single<RegisterStatus>
    fun getDataUser() : Single<List<DataUserResponse>>
    fun addFavourite(id : String) : Single<FavouriteResponse>
    fun deleteFavourite(id : String) : Single<DeleteFavouriteResponse>
    fun getFavourite() : Single<List<Movie>>
    fun checkFavourite(id : String) : Single<CheckFavouriteResponse>
    fun searchMovie(page: Int, searchMovies : SearchMovies): Single<MoviesResponse>
    fun getCommentMovie(id : String): Single<List<GetCommentResponse>>
    fun postCommentMovie(postComment : PostComment): Single<PostCommentResponse>


    fun getAllJob(): Single<List<JobResponse>>
    fun getJobByTag(data: String): Single<List<JobResponse>>
    fun getJobByAddress(data: String): Single<List<JobResponse>>
    fun getJobById(data: String): Single<List<JobResponse>>
    fun searchJob(search: String,  tag: String,  address: String): Single<List<JobResponse>>
    fun upCV(data: UpCV): Single<RegisterStatus>
    fun getCVDetail(data: String): Single<List<CvResponse>>
    fun getJobApplied(data: String): Single<List<JobResponse>>
    fun applyCV(username: String, id : String): Single<RegisterStatus>
    fun deleteCV(username: String): Single<RegisterStatus>

    fun postJob(job: Job): Single<RegisterStatus>

//    fun getAllPosted(is_approved: String, username: String): Single<List<JobResponse>>
    fun getAllPostByUser(is_approved: String, username: String): Single<List<JobResponse>>
    fun getEmployee(data: String): Single<List<CvResponse>>

    fun putStatus(username: String, id : String, status : String): Single<RegisterStatus>
    fun getStatus(username: String, id : String): Single<Status>


}