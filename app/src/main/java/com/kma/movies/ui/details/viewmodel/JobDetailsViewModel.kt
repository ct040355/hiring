package com.kma.movies.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import com.kma.movies.data.Resource
import com.kma.movies.domain.interactor.*
import com.kma.movies.model.CvResponse
import com.kma.movies.model.DataUserResponse
import com.kma.movies.model.JobResponse
import com.kma.movies.model.RegisterStatus
import com.kma.movies.model.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JobDetailsViewModel(
    private val getSingleMovieUseCase: GetJobDetailUseCase,
    private val profileUseCase: ProfileUseCase,
    private val applyJobUseCase: ApplyJobUseCase,
    private val getCvUseCase: GetCvUseCase,
    private val getStatusUsecase: GetStatusUsecase,

    ) : ViewModel() {

    private val singleJobStateFlow = MutableStateFlow<Resource<JobResponse>>(Resource.empty())

    var disposable: Disposable? = null

    val singleJobState: StateFlow<Resource<JobResponse>>
        get() = singleJobStateFlow

    fun fetchSingleJob(id: String) {
        singleJobStateFlow.value = Resource.loading()

        disposable = getSingleMovieUseCase.execute(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                if (res.isEmpty())
                    singleJobStateFlow.value = Resource.error("server")
                else
                    singleJobStateFlow.value = Resource.success(res[0])

            }, { throwable ->
                throwable.localizedMessage?.let {
                    singleJobStateFlow.value = Resource.error(it)
                }
            })
    }


    private val stateFlowAccount =
        MutableStateFlow<Resource<List<DataUserResponse>>>(Resource.empty())

    val profileStateAccount: StateFlow<Resource<List<DataUserResponse>>>
        get() = stateFlowAccount

    fun getCurrent() {
        stateFlowAccount.value = Resource.loading()
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
    private val stateFlowApply =
        MutableStateFlow<Resource<RegisterStatus>>(Resource.empty())

    val applyStateAccount: StateFlow<Resource<RegisterStatus>>
        get() = stateFlowApply

    fun applyJob(username: String, id: String) {
        stateFlowApply.value = Resource.loading()
        disposable = applyJobUseCase.execute(username, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                stateFlowApply.value = Resource.success(res)
                stateFlowApply.value = Resource.empty()
            }, { throwable ->
                throwable.localizedMessage?.let {
                    stateFlowApply.value = Resource.error(it)
                    stateFlowApply.value = Resource.empty()
                }
            })
    }

    private val stateFlow = MutableStateFlow<Resource<CvResponse>>(Resource.empty())
    val profileState: StateFlow<Resource<CvResponse>>
        get() = stateFlow

    fun getCv(data : String) {
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