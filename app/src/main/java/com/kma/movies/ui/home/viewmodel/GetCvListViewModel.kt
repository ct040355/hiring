package com.kma.movies.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kma.movies.data.sources.remote.CommonSharedPreferences
import com.kma.movies.data.Resource
import com.kma.movies.domain.interactor.GetCvListUsecase
import com.kma.movies.domain.interactor.GetCvUseCase
import com.kma.movies.domain.interactor.ProfileUseCase
import com.kma.movies.domain.interactor.UpCvUseCase
import com.kma.movies.model.CvResponse
import com.kma.movies.model.DataUserResponse
import com.kma.movies.model.RegisterStatus
import com.kma.movies.model.UpCV
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GetCvListViewModel(private val getCvUseCase: GetCvListUsecase,
    ) : ViewModel() {

    private val stateFlow = MutableStateFlow<Resource<List<CvResponse>>>(Resource.empty())
    var disposable: Disposable? = null

    val listState: StateFlow<Resource<List<CvResponse>>>
        get() = stateFlow

    fun getCvList(data : String) {
        stateFlow.value = Resource.loading()
        disposable = getCvUseCase.execute(data)
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