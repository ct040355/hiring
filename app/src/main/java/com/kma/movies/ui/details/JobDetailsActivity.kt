package com.kma.movies.ui.details

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kma.movies.R
import com.kma.movies.base.BaseActivity
import com.kma.movies.common.glide.load
import com.kma.movies.common.utils.ColorUtils.darken
import com.kma.movies.common.utils.gone
import com.kma.movies.common.utils.setAnchorId
import com.kma.movies.common.utils.visible
import com.kma.movies.data.Resource
import com.kma.movies.data.sources.remote.CommonSharedPreferences
import com.kma.movies.data.sources.remote.api.ApiClient
import com.kma.movies.databinding.ActivityMovieDetailsBinding
import com.kma.movies.model.DataUserResponse
import com.kma.movies.model.JobResponse
import com.kma.movies.model.Status
import com.kma.movies.ui.details.viewmodel.JobDetailsViewModel
import com.kma.movies.ui.home.ListEmployeeActivity
import com.kma.movies.ui.home.master.ArrayStringAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class JobDetailsActivity : BaseActivity() {

    private val jobDetailsViewModel: JobDetailsViewModel by viewModel()
    private lateinit var binding: ActivityMovieDetailsBinding
    private val args: JobDetailsActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        clearStatusBar()

        lifecycleScope.launch {
            jobDetailsViewModel.singleJobState.collect {
                handleSingleJobDataState(it)
            }
        }

        lifecycleScope.launch {
            jobDetailsViewModel.profileStateAccount.collect {
                handleDataState(it)
            }
        }

        lifecycleScope.launch {
            jobDetailsViewModel.getStatusState.collect {
                handleGetStatusState(it)
            }
        }
        lifecycleScope.launch {
            jobDetailsViewModel.applyStateAccount.collect {
                when (it.status) {
                    Resource.Status.LOADING -> {

                    }

                    Resource.Status.SUCCESS -> {
                        binding.btnApply.text = "Đã ứng tuyển"
                        binding.btnApply.isEnabled = false
                    }

                    Resource.Status.ERROR -> {

                    }

                    Resource.Status.EMPTY -> {
                        Timber.d("Empty state.")
                    }
                }
            }
        }
        lifecycleScope.launch {
            jobDetailsViewModel.profileState.collect {
                if (it.status ==  Resource.Status.ERROR) {
                    binding.btnApply.setOnClickListener {
                        Snackbar.make(binding.root, "Vui lòng tạo hồ sơ", Snackbar.LENGTH_LONG)
                            .setAnchorId(R.id.bottom_navigation).show()
                    }
                }
            }
        }
        jobDetailsViewModel.fetchSingleJob(args.id.toString())
        jobDetailsViewModel.getCurrent()
    }

    private fun handleDataState(state: Resource<List<DataUserResponse>>) {
        when (state.status) {
            Resource.Status.LOADING -> {
            }
            Resource.Status.SUCCESS -> {
                if (state.data?.size == 1) {
                    jobDetailsViewModel.getStatus(state.data[0].username!!,args.id.toString() )
                    if (state.data[0].role.equals("employer", true)) {
                        if (args.isApply) {
                            binding.btnApply.visible()
                            binding.btnApply.text = "Danh sách ứng viên"
                            binding.btnApply.setOnClickListener {
                                val intent = Intent(this@JobDetailsActivity, ListEmployeeActivity::class.java)
                                intent.putExtra("poster_id", args.id)
                                startActivity(intent)
                            }
                        } else {
                            binding.btnApply.gone()
                        }
                    } else {
                        val data = applied.filter { it == state.data[0].username  }
                        if (data.isEmpty()) {
                            binding.btnApply.text = "Ứng tuyển"
                            binding.btnApply.isEnabled = true
                            binding.btnApply.visible()
                            binding.btnApply.setOnClickListener {
                                state.data[0].username?.let { it1 ->
                                    jobDetailsViewModel.applyJob(
                                        it1, args.id)
                                }
                            }
                        } else {
                            binding.btnApply.text = "Đã ứng tuyển"
                            binding.btnApply.isEnabled = false
                        }
                    }

                } else {
                    binding.btnApply.gone()
                    CommonSharedPreferences.getInstance().remoCurrrentName()
                }
            }

            Resource.Status.ERROR -> {

            }

            Resource.Status.EMPTY -> {
                Timber.d("Empty state.")
            }
        }
    }

    private fun handleSingleJobDataState(state: Resource<JobResponse>) {
        when (state.status) {
            Resource.Status.LOADING -> {
            }

            Resource.Status.SUCCESS -> {
                loadMovieData(state.data)
                jobDetailsViewModel.getCurrent()

            }

            Resource.Status.ERROR -> {
                Toast.makeText(this, "Error: ${state.message}", Toast.LENGTH_LONG).show()
                onBackPressed()
            }
        }
    }
    private var applied: ArrayList<String> = arrayListOf()
    private fun loadMovieData(data: JobResponse?) {
        data?.let {
            binding.tvTitle.text = data.title
            binding.tvAddress.text = data.address
            binding.tvCompany.text = data.company
            binding.tvContent.text = data.descrip
            binding.tvExp.text = data.exp
            binding.tvToolbarTitle.text = data.title
            binding.tvDeadline.text = "Hạn nộp hồ sơ: " + data.deadline
            binding.tvApplying.text = "Số ứng tuyển: " + data.applied.size
            binding.tvSalary.text = data.salary
            binding.ivActivityMovieDetails.load(url = ApiClient.POSTER_BASE_URL + data.image)
            data.responsive?.let {
                binding.rcvResponsive.layoutManager = LinearLayoutManager(this)
                binding.rcvResponsive.adapter = ArrayStringAdapter(this, it.split("\n"))
            }
            data.benefit?.let {
                binding.rcvBenefit.layoutManager = LinearLayoutManager(this)
                binding.rcvBenefit.adapter = ArrayStringAdapter(this, it.split("\n"))
            }
            applied.clear()
            applied.addAll(data.applied)
        }
    }


    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.imvBack.setOnClickListener {
            onBackPressed()
        }
        val color = ContextCompat.getColor(this, R.color.brand_color_2)
        window?.statusBarColor = color
    }

    private fun handleGetStatusState(state: Resource<Status>) {
        when (state.status) {
            Resource.Status.LOADING -> {
            }

            Resource.Status.SUCCESS -> {
                binding.tvStatus.visible()
                binding.tvStatus.text = state.data?.apply_status

            }

            Resource.Status.ERROR -> {
                binding.tvStatus.gone()
            }

            Resource.Status.EMPTY -> {

            }
        }
    }



}