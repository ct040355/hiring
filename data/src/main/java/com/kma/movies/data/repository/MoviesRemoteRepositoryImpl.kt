package com.kma.movies.data.repository

import com.google.gson.Gson
import com.kma.movies.data.sources.remote.api.ApiClient
import com.kma.movies.data.sources.remote.mapper.MoviesRemoteMapper
import com.kma.movies.data.sources.remote.model.MovieIdDto
import com.kma.movies.data.sources.remote.model.RemotePostComment
import com.kma.movies.domain.repository.MoviesRemoteRepository
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody


class MoviesRemoteRepositoryImpl(private val moviesRemoteMapper: MoviesRemoteMapper) :
    MoviesRemoteRepository {

    override fun getPopularMovies(page: Int): Single<MoviesResponse> {
        return ApiClient.movieService().getPopularMovies(page).map {
            moviesRemoteMapper.mapFromRemote(it)
        }
    }

    override fun getUpcomingMovies(page: Int): Single<MoviesResponse> {
        return ApiClient.movieService().getUpcomingMovies(page).map {
            moviesRemoteMapper.mapFromRemote(it)
        }
    }

    override fun getSingleMovie(id: String): Single<MovieDetail> {
        return ApiClient.movieService().getSingleMovie(id).map {
            moviesRemoteMapper.mapDetailFromRemote(it)
        }
    }

    override fun registerUser(userRegister: UserRegister): Single<RegisterStatus> {
        return ApiClient.movieService().registerUser(
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", userRegister.username)
                .addFormDataPart("password", userRegister.password)
                .addFormDataPart("name", userRegister.name)
                .addFormDataPart("role", userRegister.role)
                .build()
        ).map {
            moviesRemoteMapper.mapRegisterFromRemote(it)
        }
    }

    override fun loginUser(userLogin: UserLogin): Single<RegisterStatus> {
        return ApiClient.movieService()
            .loginUser(
                MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", userLogin.username)
                .addFormDataPart("password", userLogin.password)
                .build()).map {
                moviesRemoteMapper.mapRegisterFromRemote(it)
            }
    }

    override fun getDataUser(): Single<List<DataUserResponse>> {
        return ApiClient.movieService()
            .getDataUser(
                "True"
            ).map {
                moviesRemoteMapper.mapDataUserFromRemote(it)
            }
    }

    override fun addFavourite(id: String): Single<FavouriteResponse> {
        return ApiClient.movieService()
            .setFavourite(MovieIdDto(id)).map {
                moviesRemoteMapper.mapFavouriteFromRemote(it)
            }
    }

    override fun getFavourite(): Single<List<Movie>> {
        return ApiClient.movieService().getFavourite().map {
            moviesRemoteMapper.mapFavouritesFromRemote(it)
        }
    }

    override fun checkFavourite(id: String): Single<CheckFavouriteResponse> {
        return ApiClient.movieService().checkFavourite(id).map {
            moviesRemoteMapper.mapCheckFavouriteFromRemote(it)
        }
    }

    override fun searchMovie(page: Int, searchMovies: SearchMovies): Single<MoviesResponse> {
        return ApiClient.movieService().searchMovie(searchMovies.query ?: "", page).map {
            moviesRemoteMapper.mapFromRemote(it)
        }
    }


    override fun deleteFavourite(id: String): Single<DeleteFavouriteResponse> {
        return ApiClient.movieService().deleteFavourite(MovieIdDto(id)).map {
            moviesRemoteMapper.mapDeleteFavouritesFromRemote(it)
        }
    }


    override fun getCommentMovie(
        id: String
    ): Single<List<GetCommentResponse>> {
        return ApiClient.movieService().getCommentMovie(id).map {
            moviesRemoteMapper.mapGetCommentFromRemote(it).reversed()
        }
    }

    override fun postCommentMovie(
        postComment: PostComment
    ): Single<PostCommentResponse> {
        return ApiClient.movieService()
            .postCommentMovie(RemotePostComment(postComment.movieId, postComment.content)).map {
            moviesRemoteMapper.mapPostCommentFromRemote(it)
        }
    }

    override fun getAllJob(): Single<List<JobResponse>> {
        return ApiClient.movieService().getAllJob().map {
            moviesRemoteMapper.mapGetJobList(it)
        }
    }

    override fun getJobByTag(data: String): Single<List<JobResponse>> {
        return ApiClient.movieService().getJobByTag(data).map {
            moviesRemoteMapper.mapGetJobList(it)
        }
    }

    override fun getJobByAddress(data: String): Single<List<JobResponse>> {
        return ApiClient.movieService().getJobByAddress(data).map {
            moviesRemoteMapper.mapGetJobList(it)
        }
    }

    override fun getJobById(data: String): Single<List<JobResponse>> {
        return ApiClient.movieService().getJobById(data).map {
            moviesRemoteMapper.mapGetJobList(it)
        }
    }

    override fun searchJob(
        search: String,
        tag: String,
        address: String
    ): Single<List<JobResponse>> {
        return ApiClient.movieService().searchJob(search, tag, address).map {
            moviesRemoteMapper.mapGetJobList(it)
        }
    }

    override fun upCV(data: UpCV): Single<RegisterStatus> {
        val education = Gson().toJson(data.education)
        val experience = Gson().toJson(data.experience)
        val builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
        builder.addFormDataPart("name", data.name.orEmpty())
            .addFormDataPart("description", data.description.orEmpty())
            .addFormDataPart("location", data.location.orEmpty())
            .addFormDataPart("salary", data.salary.orEmpty())
            .addFormDataPart("gender", data.gender.orEmpty())
            .addFormDataPart("type", data.type.orEmpty())
            .addFormDataPart("qualify", data.qualify.orEmpty())
            .addFormDataPart("exp", data.exp.orEmpty())
            .addFormDataPart("tag", data.tag.orEmpty())
            .addFormDataPart("education", education)
            .addFormDataPart("experience", experience)
        if (data.image != null) {
            builder.addFormDataPart("image", data.image?.name.orEmpty(),
                RequestBody.create("image/*".toMediaTypeOrNull(), data.image!!))
        }
        return ApiClient.movieService().upCV(
            builder.build()).map {
            moviesRemoteMapper.mapRegisterFromRemote(it)
        }
    }

    override fun getCVDetail(data: String): Single<List<CvResponse>> {
        return ApiClient.movieService().getCVDetail(
            data).map {
            moviesRemoteMapper.mapGetCVRemote(it)
        }
    }

    override fun getJobApplied(data: String): Single<List<JobResponse>> {
        return ApiClient.movieService().getJobApplied(data).map {
            moviesRemoteMapper.mapGetJobList(it)
        }
    }

    override fun applyCV(username: String, id: String): Single<RegisterStatus> {
        return ApiClient.movieService().applyCV(username, id).map {
            moviesRemoteMapper.mapRegisterFromRemote(it)
        }
    }

    override fun deleteCV(username: String): Single<RegisterStatus> {
        return ApiClient.movieService().deleteCV(username).map {
            moviesRemoteMapper.mapRegisterFromRemote(it)
        }
    }

    override fun postJob(job: Job): Single<RegisterStatus> {
        val builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
        builder.addFormDataPart("title", job.title.orEmpty())
            .addFormDataPart("descrip", job.descrip.orEmpty())
            .addFormDataPart("tag", job.tag.orEmpty())
            .addFormDataPart("address", job.address.orEmpty())
            .addFormDataPart("responsive", job.responsive.orEmpty())
            .addFormDataPart("salary", job.salary.orEmpty())
            .addFormDataPart("company", job.company.orEmpty())
            .addFormDataPart("benefit", job.benefit.orEmpty())
            .addFormDataPart("exp", job.exp.orEmpty())
            .addFormDataPart("deadline", job.deadline.orEmpty())
        if (job.image != null) {
            builder.addFormDataPart("image", job.image?.name.orEmpty(),
                RequestBody.create("image/*".toMediaTypeOrNull(), job.image!!))
        }

        return ApiClient.movieService().postJob(builder.build()).map {
            moviesRemoteMapper.mapRegisterFromRemote(it)
        }
    }

    override fun getAllPostByUser(
        is_approved: String,
        username: String
    ): Single<List<JobResponse>> {
        return ApiClient.movieService().getAllPostByUser(is_approved, username).map {
            moviesRemoteMapper.mapGetJobList(it)
        }
    }

    override fun getEmployee(data: String): Single<List<CvResponse>> {
        return ApiClient.movieService().getEmployee(
            data).map {
            moviesRemoteMapper.mapGetCVRemote(it)
        }
    }

    override fun putStatus(username: String, id: String, status: String): Single<RegisterStatus> {
        return ApiClient.movieService().putStatus(
            status,
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("post_id",id)
                .build()).map {
            moviesRemoteMapper.mapRegisterFromRemote(it)
        }
    }

    override fun getStatus(username: String, id: String): Single<Status> {
        return ApiClient.movieService().getStatus(
            username,
            id).map {
            moviesRemoteMapper.mapStatusFromRemote(it)
        }
    }


}