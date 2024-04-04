package com.kma.movies.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kma.movies.data.sources.remote.CommonSharedPreferences
import com.kma.movies.data.Resource
import com.kma.movies.domain.interactor.ProfileUseCase
import com.kma.movies.domain.interactor.UpCvUseCase
import com.kma.movies.model.DataUserResponse
import com.kma.movies.model.RegisterStatus
import com.kma.movies.model.UpCV
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PostCvViewModel(private val upCvUseCase: UpCvUseCase,
                      private val profileUseCase: ProfileUseCase
    ) : ViewModel() {

    private val stateFlow = MutableStateFlow<Resource<RegisterStatus>>(Resource.empty())
    var disposable: Disposable? = null

    val profileState: StateFlow<Resource<RegisterStatus>>
        get() = stateFlow

    fun postData(data : UpCV) {
        stateFlow.value = Resource.loading()
        disposable = upCvUseCase.execute(data)
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
    private val stateFlowAccount = MutableStateFlow<Resource<List<DataUserResponse>>>(Resource.empty())

    val profileStateAccount: StateFlow<Resource<List<DataUserResponse>>>
        get() = stateFlowAccount

    fun getCurrent() {
        stateFlow.value = Resource.loading()
        disposable = profileUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                stateFlowAccount.value = Resource.success(res)
            }, { throwable ->
                throwable.localizedMessage?.let {
                    stateFlowAccount.value = Resource.error(it)
                }
            })
    }

}