package com.kma.movies.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kma.movies.R
import com.kma.movies.base.BaseActivity
import com.kma.movies.common.utils.gone
import com.kma.movies.common.utils.invisible
import com.kma.movies.common.utils.setAnchorId
import com.kma.movies.common.utils.visible
import com.kma.movies.data.Resource
import com.kma.movies.databinding.ActivityListEmployeeBinding
import com.kma.movies.databinding.ActivityMovieDetailsBinding
import com.kma.movies.model.CvResponse
import com.kma.movies.model.JobResponse
import com.kma.movies.model.RegisterStatus
import com.kma.movies.ui.details.viewmodel.JobDetailsViewModel
import com.kma.movies.ui.home.master.ListEmployeeAdapter
import com.kma.movies.ui.home.viewmodel.GetCvListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ListEmployeeActivity : BaseActivity() {
    private val jobDetailsViewModel: GetCvListViewModel by viewModel()
    private lateinit var binding: ActivityListEmployeeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val color = ContextCompat.getColor(this, R.color.brand_color_2)
        window?.statusBarColor = color
        setupRecyclerView()
        binding.imvBack.setOnClickListener {
            onBackPressed()
        }
        lifecycleScope.launch {
            jobDetailsViewModel.listState.collect {
                handleFavoriteMoviesDataState(it)
            }
        }
    }


    private fun handleFavoriteMoviesDataState(state: Resource<List<CvResponse>>) {
        when (state.status) {
            Resource.Status.LOADING -> {
            }
            Resource.Status.SUCCESS -> {
                if (state.data.isNullOrEmpty()) {
                    binding.layoutEmpty.visible()
                    binding.rcvList.gone()
                } else {
                    binding.layoutEmpty.gone()
                    binding.rcvList.visible()
                    loadMovies(state.data)
                }
            }
            Resource.Status.ERROR -> {
                binding.layoutEmpty.visible()
                binding.rcvList.gone()
            }
            Resource.Status.EMPTY -> {
                Timber.d("Empty state.")
            }
        }
    }

    private var adapter : ListEmployeeAdapter?= null
    private fun loadMovies(movies: List<CvResponse>?) {
        movies?.let {
            adapter?.clear()
            adapter?.fillList(it)
        }
    }


    override fun onResume() {
        super.onResume()
        intent.getStringExtra("poster_id")?.let { jobDetailsViewModel.getCvList(it) }
    }
    private fun setupRecyclerView() {
        adapter = ListEmployeeAdapter(this, listOf())
        adapter?.setOnMovieClickListener(object : ListEmployeeAdapter.OnItemClickListener{
            override fun onItemClick(data: CvResponse) {
                val intent = Intent(this@ListEmployeeActivity, ProfileEmployeeActivity::class.java)
                intent.putExtra("username", data.username)
                intent.putExtra("post_id", this@ListEmployeeActivity.intent.getStringExtra("poster_id"))
                startActivity(intent)
            }

        })
        binding.rcvList.adapter = adapter
        binding.rcvList.layoutManager = LinearLayoutManager(this)
    }

}