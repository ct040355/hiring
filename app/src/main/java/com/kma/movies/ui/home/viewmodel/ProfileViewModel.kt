package com.kma.movies.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kma.movies.data.sources.remote.CommonSharedPreferences
import com.kma.movies.data.Resource
import com.kma.movies.domain.interactor.ProfileUseCase
import com.kma.movies.model.DataUserResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel(private val profileUseCase: ProfileUseCase) : ViewModel() {

    private val stateFlow = MutableStateFlow<Resource<List<DataUserResponse>>>(Resource.empty())
    var disposable: Disposable? = null

    val profileState: StateFlow<Resource<List<DataUserResponse>>>
        get() = stateFlow

    fun getData() {
        stateFlow.value = Resource.loading()
        disposable = profileUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                stateFlow.value = Resource.success(res)
            }, { throwable ->
                Log.d("XUANNNV", "postData: $throwable ")
                throwable.localizedMessage?.let {
                    stateFlow.value = Resource.error(it)
                }
            })
    }

}