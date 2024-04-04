package com.kma.movies.di

import com.kma.movies.data.repository.MoviesLocalRepositoryImpl
import com.kma.movies.data.repository.MoviesRemoteRepositoryImpl
import com.kma.movies.data.sources.local.mapper.MoviesLocalMapper
import com.kma.movies.data.sources.remote.mapper.MoviesRemoteMapper
import com.kma.movies.domain.interactor.*
import com.kma.movies.domain.repository.MoviesLocalRepository
import com.kma.movies.domain.repository.MoviesRemoteRepository
import com.kma.movies.ui.comment.CommentViewModel
import com.kma.movies.ui.details.viewmodel.CommentListAdapter
import com.kma.movies.ui.search.SearchJobViewModel
import com.kma.movies.ui.details.viewmodel.JobDetailsViewModel
import com.kma.movies.ui.home.PostJobViewModel
import com.kma.movies.ui.home.master.CategoryListAdapter
import com.kma.movies.ui.home.master.ListJobAdapter
import com.kma.movies.ui.home.master.MovieListAdapter
import com.kma.movies.ui.home.viewmodel.ListJobViewModel
import com.kma.movies.ui.home.viewmodel.CategoryViewModel
import com.kma.movies.ui.home.viewmodel.GetCvListViewModel
import com.kma.movies.ui.home.viewmodel.GetCvViewModel
import com.kma.movies.ui.home.viewmodel.PostCvViewModel
import com.kma.movies.ui.home.viewmodel.ProfileViewModel
import com.kma.movies.ui.home.viewmodel.UpcomingViewModel
import com.kma.movies.ui.sign_in.viewmodel.LoginViewModel
import com.kma.movies.ui.sign_in.viewmodel.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { MoviesRemoteMapper() }
    single { MoviesLocalMapper() }
    factory<MoviesRemoteRepository> { MoviesRemoteRepositoryImpl(get()) }
    factory<MoviesLocalRepository> { MoviesLocalRepositoryImpl(androidContext(), get()) }
    factory { MovieListAdapter(androidContext()) }
    factory { ListJobAdapter(androidContext()) }
    factory { CategoryListAdapter(androidContext()) }
    factory { CommentListAdapter(androidContext()) }
}

val popularMoviesModule = module {
    factory { GetPopularMoviesUseCase(get()) }
    viewModel { CategoryViewModel() }
}
val postJobModule = module {
    factory { PostJobUseCase(get()) }
    viewModel { PostJobViewModel(get()) }
}

val postCvModule = module {
    factory { UpCvUseCase(get()) }
    factory { ProfileUseCase(get()) }
    viewModel { PostCvViewModel(get(), get()) }
}
val getCvModule = module {
    factory { GetCvUseCase(get()) }
    factory { DeleteUseCase(get()) }
    factory { ApplyStatusUsecase(get()) }
    factory { GetStatusUsecase(get()) }
    viewModel { GetCvViewModel(get(), get(), get(), get()) }
}
val getCvListModule = module {
    factory { GetCvListUsecase(get()) }
    viewModel { GetCvListViewModel(get()) }
}

val upcomingMoviesModule = module {
    factory { GetUpcomingMoviesUseCase(get()) }
    viewModel { UpcomingViewModel(get()) }
}

val favoriteMoviesModule = module {
    factory { ListJobUseCase(get()) }
    factory { ListJobByTagUseCase(get()) }
    factory { ListJobAppliedUseCase(get()) }
    factory { AllJobByUserUseCase(get()) }
    viewModel { ListJobViewModel(get(), get(), get(), get()) }
}
val registerMoviesModule = module {
    factory { UserRegisterUseCase(get()) }
    viewModel { SignUpViewModel(get()) }


}
val signUpMoviesModule = module {
    factory { UserLoginUseCase(get()) }
    viewModel { LoginViewModel(get()) }
}
val profileModule = module {
    factory { ProfileUseCase(get()) }
    viewModel { ProfileViewModel(get()) }
}
val searchModule = module {
    factory { SearchJobUseCase(get()) }
    viewModel { SearchJobViewModel(get()) }
}
val getCommentModule = module {
    factory { GetCommentMovieUseCase(get()) }
    factory { PostCommentMovieUseCase(get()) }
    viewModel { CommentViewModel(get(), get()) }
}

val movieDetailsModule = module {
    factory { GetJobDetailUseCase(get()) }
    factory { ProfileUseCase(get()) }
    factory { ApplyJobUseCase(get()) }
    factory { GetCvUseCase(get()) }
    factory { GetStatusUsecase(get()) }
    viewModel { JobDetailsViewModel(get(), get(), get(), get(), get()) }
}