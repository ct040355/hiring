package com.kma.movies.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kma.movies.data.sources.remote.CommonSharedPreferences
import com.kma.movies.data.Resource
import com.kma.movies.domain.interactor.ApplyStatusUsecase
import com.kma.movies.domain.interactor.DeleteUseCase
import com.kma.movies.domain.interactor.GetCvUseCase
import com.kma.movies.domain.interactor.GetStatusUsecase
import com.kma.movies.domain.interactor.ProfileUseCase
import com.kma.movies.domain.interactor.UpCvUseCase
import com.kma.movies.model.CvResponse
import com.kma.movies.model.DataUserResponse
import com.kma.movies.model.RegisterStatus
import com.kma.movies.model.Status
import com.kma.movies.model.UpCV
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GetCvViewModel(
    private val getCvUseCase: GetCvUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val aplyStatusUsecase: ApplyStatusUsecase,
    private val getStatusUsecase: GetStatusUsecase,
) : ViewModel() {
    var disposable: Disposable? = null

    private val stateFlow = MutableStateFlow<Resource<CvResponse>>(Resource.empty())

    val profileState: StateFlow<Resource<CvResponse>>
        get() = stateFlow

    fun getCv(data: String) {
        stateFlow.value = Resource.loading()
        disposable = getCvUseCase.execute(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                if (res.isEmpty()) {
                    stateFlow.value = Resource.error("Username error")
                    return@subscribe
                }
                stateFlow.value = Resource.success(res[0])
            }, { throwable ->
                throwable.localizedMessage?.let {
                    stateFlow.value = Resource.error(it)
                }
            })
    }




    private val deleteFlow = MutableStateFlow<Resource<RegisterStatus>>(Resource.empty())

    val deleteState: StateFlow<Resource<RegisterStatus>>
        get() = deleteFlow

    fun deleteCV(data: String) {
        deleteFlow.value = Resource.loading()
        disposable = deleteUseCase.execute(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->

                deleteFlow.value = Resource.success(res)
            }, { throwable ->
                throwable.localizedMessage?.let {
                    deleteFlow.value = Resource.error(it)
                }
            })
    }


    private val statusFlow = MutableStateFlow<Resource<RegisterStatus>>(Resource.empty())

    val statusState: StateFlow<Resource<RegisterStatus>>
        get() = statusFlow

    fun putStatus(username: String, id : String, status : String) {
        statusFlow.value = Resource.loading()
        disposable = aplyStatusUsecase.execute(username, id, status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->

                statusFlow.value = Resource.success(res)
            }, { throwable ->
                throwable.localizedMessage?.let {
                    statusFlow.value = Resource.error(it)
                }
            })
    }

    private val getStatusFlow = MutableStateFlow<Resource<Status>>(Resource.empty())

    val getStatusState: StateFlow<Resource<Status>>
        get() = getStatusFlow

    fun getStatus(username: String, id : String) {
        getStatusFlow.value = Resource.loading()
        disposable = getStatusUsecase.execute(username, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->

                getStatusFlow.value = Resource.success(res)
            }, { throwable ->
                throwable.localizedMessage?.let {
                    getStatusFlow.value = Resource.error(it)
                }
            })
    }

}