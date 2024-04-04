package com.kma.movies.ui.search

import androidx.lifecycle.ViewModel
import com.kma.movies.data.Resource
import com.kma.movies.domain.interactor.SearchJobUseCase
import com.kma.movies.model.JobResponse
import com.kma.movies.model.Movie
import com.kma.movies.model.SearchMovies
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchJobViewModel(
    private val searchJobUseCase: SearchJobUseCase
) : ViewModel() {

    private val stateFlow = MutableStateFlow<Resource<List<JobResponse>>>(Resource.empty())
    var disposable: Disposable? = null

    val searchMoviesState: StateFlow<Resource<List<JobResponse>>>
        get() = stateFlow

    fun fetchSearchJob(search: String, tag: String, address: String) {
        stateFlow.value = Resource.loading()
        disposable = searchJobUseCase.execute(search, tag, address)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                stateFlow.value = Resource.success(res)
            }, { throwable ->
                throwable.localizedMessage?.let {
                    stateFlow.value = Resource.error(it)
                }
            })
    }


}