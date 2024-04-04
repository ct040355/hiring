package com.kma.movies.data.sources.remote.service

import com.kma.movies.data.sources.remote.model.RemoteJobResponse
import com.kma.movies.data.sources.remote.model.MovieIdDto
import com.kma.movies.data.sources.remote.model.RemoteCheckFavouriteResponse
import com.kma.movies.data.sources.remote.model.RemoteCvResponse
import com.kma.movies.data.sources.remote.model.RemoteFavouriteResponse
import com.kma.movies.data.sources.remote.model.RemoteDataUserResponse
import com.kma.movies.data.sources.remote.model.RemoteDeleteFavouriteResponse
import com.kma.movies.data.sources.remote.model.RemoteGetCommentResponse
import com.kma.movies.data.sources.remote.model.RemoteMovie
import com.kma.movies.data.sources.remote.model.RemoteMovieDetail
import com.kma.movies.data.sources.remote.model.RemoteMoviesResponse
import com.kma.movies.data.sources.remote.model.RemotePostComment
import com.kma.movies.data.sources.remote.model.RemotePostCommentResponse
import com.kma.movies.data.sources.remote.model.RemoteResponse
import com.kma.movies.data.sources.remote.model.StatusResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @PUT("/cv/status/{status}")
    fun putStatus(
        @Path("status") status: String,
        @Body requestBody: RequestBody
    ): Single<RemoteResponse>

    @GET("/cv/status/{username}/{post_id}")
    fun getStatus(
        @Path("username") username: String,
        @Path("post_id") post_id: String,
    ): Single<StatusResponse>

    @GET("post")
    fun getAllJob(): Single<List<RemoteJobResponse>>

    @GET("post")
    fun getJobByTag(@Query("tag") tag: String): Single<List<RemoteJobResponse>>

    @GET("post")
    fun searchJob(
        @Query("search") search: String,
        @Query("tag") tag: String,
        @Query("address") address: String
    ): Single<List<RemoteJobResponse>>

    @GET("post")
    fun getJobByAddress(@Query("address") address: String): Single<List<RemoteJobResponse>>

    @GET("post")
    fun getJobById(@Query("id") id: String): Single<List<RemoteJobResponse>>

    @GET("post")
    fun getAllPosted(
        @Query("is_approved") id: String,
        @Query("username") username: String
    ): Single<List<RemoteJobResponse>>

    @GET("post")
    fun getAllPostByUser(
        @Query("is_approved") id: String,
        @Query("username") username: String
    ): Single<List<RemoteJobResponse>>


    @GET("post")
    fun getJobApplied(@Query("applying_cv") username: String): Single<List<RemoteJobResponse>>

    @POST("post")
    fun postJob(
        @Body requestBody: RequestBody
    ): Single<RemoteResponse>

    @POST("api/signup")
    fun registerUser(
        @Body requestBody: RequestBody
    ): Single<RemoteResponse>

    @POST("checklogin")
    fun loginUser(
        @Body requestBody: RequestBody
    ): Single<RemoteResponse>


    @PUT("apply/{username}/{id}")
    fun applyCV(
        @Path("username") username: String, @Path("id") id: String
    ): Single<RemoteResponse>

    @DELETE("cv/{username}")
    fun deleteCV(@Path("username") username: String): Single<RemoteResponse>


    @POST("/cv")
    fun upCV(
        @Body requestBody: RequestBody
    ): Single<RemoteResponse>

    @GET("/cv")
    fun getCVDetail(
        @Query("username") username: String
    ): Single<List<RemoteCvResponse>>

    @GET("/cv")
    fun getEmployee(
        @Query("applied_post") id: String
    ): Single<List<RemoteCvResponse>>

    @GET("personal")
    fun getDataUser(@Query("current_user") current_user: String): Single<List<RemoteDataUserResponse>>

    @POST("movies/favorites")
    fun setFavourite(
        @Body movieIdDto: MovieIdDto
    ): Single<RemoteFavouriteResponse>

    @GET("movies/favorites")
    fun getFavourite(): Single<List<RemoteMovie>>

    @HTTP(method = "DELETE", path = "movies/favorites", hasBody = true)
    fun deleteFavourite(
        @Body movieIdDto: MovieIdDto
    ): Single<RemoteDeleteFavouriteResponse>

    @GET("movies/favorites/check/{id}")
    fun checkFavourite(
        @Path("id") id: String
    ): Single<RemoteCheckFavouriteResponse>

    @GET("movies/search")
    fun searchMovie(
        @Query("q") query: String,
        @Query("page") page: Int,
    ): Single<RemoteMoviesResponse>

    @GET("movies/comments/{id}")
    fun getCommentMovie(@Path("id") id: String): Single<List<RemoteGetCommentResponse>>

    @POST("movies/comments")
    fun postCommentMovie(@Body remotePostComment: RemotePostComment): Single<RemotePostCommentResponse>

    @GET("movies/movies")
    fun getPopularMovies(@Query("page") page: Int): Single<RemoteMoviesResponse>

    @GET("movies/movies")
    fun getUpcomingMovies(@Query("page") page: Int): Single<RemoteMoviesResponse>

    @GET("movies/movies/{id}")
    fun getSingleMovie(@Path("id") id: String): Single<RemoteMovieDetail>

}