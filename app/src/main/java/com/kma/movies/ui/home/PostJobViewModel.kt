package com.kma.movies.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kma.movies.data.Resource
import com.kma.movies.domain.interactor.PostJobUseCase
import com.kma.movies.model.Job
import com.kma.movies.model.RegisterStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PostJobViewModel(
    private val postJobUseCase: PostJobUseCase
) : ViewModel() {

    private val stateFlow = MutableStateFlow<Resource<RegisterStatus>>(Resource.empty())
    var disposable: Disposable? = null

    val postJobState: StateFlow<Resource<RegisterStatus>>
        get() = stateFlow

    fun postData(job: Job) {
        stateFlow.value = Resource.loading()
        disposable = postJobUseCase.execute(job)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                stateFlow.value = Resource.success(res)
            }, { throwable ->
                Log.d("XUANNNV", "postData: $throwable ")
                throwable.localizedMessage?.let {
                    stateFlow.value = Resource.error(it)
                    stateFlow.value = Resource.empty()
                }
            })
    }
}

