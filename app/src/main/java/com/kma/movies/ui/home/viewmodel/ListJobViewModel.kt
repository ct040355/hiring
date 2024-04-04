package com.kma.movies.ui.home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kma.movies.R
import com.kma.movies.data.Resource
import com.kma.movies.domain.interactor.AllJobByUserUseCase
import com.kma.movies.domain.interactor.ListJobAppliedUseCase
import com.kma.movies.domain.interactor.ListJobByTagUseCase
import com.kma.movies.domain.interactor.ListJobUseCase
import com.kma.movies.model.Category
import com.kma.movies.model.JobResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListJobViewModel(private val listJobUseCase: ListJobUseCase,
                       private val listJobByTagUseCase: ListJobByTagUseCase,
                       private val listJobAppliedUseCase: ListJobAppliedUseCase,
                       private val allJobByUserUseCase: AllJobByUserUseCase,
    ) : ViewModel() {
    var disposable: Disposable? = null

    private val stateFlow = MutableStateFlow<Resource<List<JobResponse>>>(Resource.empty())

    val listJobState: StateFlow<Resource<List<JobResponse>>>
        get() = stateFlow

    fun listCategory(context : Context) : MutableList<Category> {
        return mutableListOf(
            Category(context.getString(R.string.all), R.drawable.ic_cntt),
            Category(context.getString(R.string.tag_1), R.drawable.ic_cntt),
            Category(context.getString(R.string.tag_2), R.drawable.ic_kt_tc),
            Category(context.getString(R.string.tag_3), R.drawable.ic_education),
            Category(context.getString(R.string.tag_4), R.drawable.ic_digital),
            Category(context.getString(R.string.tag_5), R.drawable.ic_telecommunication),
            Category(context.getString(R.string.tag_6), R.drawable.ic_sale),
            Category(context.getString(R.string.tag_7), R.drawable.ic_content),
            Category(context.getString(R.string.tag_8), R.drawable.ic_healthcare),
            Category(context.getString(R.string.tag_9), R.drawable.ic_logistics),
            Category(context.getString(R.string.tag_10), R.drawable.ic_build),
            Category(context.getString(R.string.tag_11), R.drawable.ic_factory),
            Category(context.getString(R.string.tag_12), R.drawable.ic_other),
        )
    }
    fun fetchListJob() {
        stateFlow.value = Resource.loading()
        disposable = listJobUseCase.execute()
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
    fun fetchListJobByTag(data : String) {
        stateFlow.value = Resource.loading()
        disposable = listJobByTagUseCase.execute(data)
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
    fun fetchListJobApplied(data : String) {
        stateFlow.value = Resource.loading()
        disposable = listJobAppliedUseCase.execute(data)
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


    private val stateFlowNew = MutableStateFlow<Resource<List<JobResponse>>>(Resource.empty())

    val listJobStateNew: StateFlow<Resource<List<JobResponse>>>
        get() = stateFlowNew


    fun fetchListJobPosted(data : String) {
        stateFlowNew.value = Resource.loading()
        disposable = allJobByUserUseCase.execute("True", data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                stateFlowNew.value = Resource.success(res)
            }, { throwable ->
                throwable.localizedMessage?.let {
                    stateFlowNew.value = Resource.error(it)
                }
            })
    }
    fun fetchListJobByUser(data : String) {
        stateFlow.value = Resource.loading()
        disposable = allJobByUserUseCase.execute("", data)
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