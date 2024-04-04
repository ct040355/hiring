package com.kma.movies.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.kma.movies.R
import com.kma.movies.base.BaseActivity
import com.kma.movies.common.utils.gone
import com.kma.movies.common.utils.invisible
import com.kma.movies.common.utils.visible
import com.kma.movies.data.Resource
import com.kma.movies.databinding.ActivityListEmployeeBinding
import com.kma.movies.databinding.ActivityProfileEmployeeBinding
import com.kma.movies.model.CvResponse
import com.kma.movies.model.Education
import com.kma.movies.model.Experience
import com.kma.movies.model.Status
import com.kma.movies.ui.home.master.EducationAdapter
import com.kma.movies.ui.home.master.ExperienceAdapter
import com.kma.movies.ui.home.viewmodel.GetCvViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProfileEmployeeActivity : BaseActivity() {
    private val getCvViewModel: GetCvViewModel by viewModel()
    private var isFirst = true
    private lateinit var binding: ActivityProfileEmployeeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val color = ContextCompat.getColor(this, R.color.brand_color_2)
        window?.statusBarColor = color
        lifecycleScope.launch {
            getCvViewModel.profileState.collect {
                handleFavoriteMoviesDataState(it)
            }
        }
        lifecycleScope.launch {
            getCvViewModel.getStatusState.collect {
                handleGetStatusState(it)
            }
        }


        binding.spStatus.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.status)
        )
        binding.spStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!isFirst) {
                    intent.getStringExtra("username")
                        ?.let { getCvViewModel.putStatus( it,intent.getStringExtra("post_id")!!,resources.getStringArray(R.array.status)[position] ) }
                }
                isFirst = false
            }

        }
        binding.imvBack.setOnClickListener {
            onBackPressed()
        }
        intent.getStringExtra("username")?.let { getCvViewModel.getCv(it) }
        intent.getStringExtra("username")
            ?.let { getCvViewModel.getStatus(it,intent.getStringExtra("post_id")!! ) }
    }

    private fun handleFavoriteMoviesDataState(state: Resource<CvResponse>) {
        when (state.status) {
            Resource.Status.LOADING -> {
            }

            Resource.Status.SUCCESS -> {
                binding.llProfileEmpty.invisible()
                binding.llContainer.visible()
                onBindData(state.data)

            }

            Resource.Status.ERROR -> {
                binding.llProfileEmpty.visible()
                binding.llContainer.invisible()
            }

            Resource.Status.EMPTY -> {
                Timber.d("Empty state.")
            }
        }
    }

    private fun handleGetStatusState(state: Resource<Status>) {
        when (state.status) {
            Resource.Status.LOADING -> {
            }

            Resource.Status.SUCCESS -> {
                binding.spStatus.visible()
                resources.getStringArray(R.array.status).forEachIndexed { index, s ->
                    if (s == state.data?.apply_status) {
                        binding.spStatus.setSelection(index)
                    }
                }
            }

            Resource.Status.ERROR -> {
                intent.getStringExtra("username")
                    ?.let { getCvViewModel.putStatus( it,intent.getStringExtra("post_id")!!,resources.getStringArray(R.array.status)[0] ) }
            }

            Resource.Status.EMPTY -> {

            }
        }
    }

    private fun onBindData(data: CvResponse?) {
        data?.let {
            binding.tvDescription.text = it.description
            binding.tvAddress.text = it.location
            binding.tvExp.text = it.exp
            binding.tvSalary.text = it.salary
            binding.tvQuality.text = it.qualify
            binding.tvGen.text = it.gender
            binding.tvType.text = it.type
            binding.tvTag.text = it.tag
            binding.tvName.text = it.name
            val gson = Gson()
            val objectList = gson.fromJson(it.education, Array<Education>::class.java).asList()
            binding.rcvEducation.layoutManager = LinearLayoutManager(this)
            binding.rcvEducation.adapter = EducationAdapter(this, objectList)
            val experienceList =
                gson.fromJson(it.experience, Array<Experience>::class.java).asList()
            binding.rcvWork.layoutManager = LinearLayoutManager(this)
            binding.rcvWork.adapter = ExperienceAdapter(this, experienceList)
        }
    }
}


