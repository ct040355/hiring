package com.kma.movies.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kma.movies.R
import com.kma.movies.common.utils.gone
import com.kma.movies.common.utils.visible
import com.kma.movies.data.Resource
import com.kma.movies.databinding.FragmentHomeBinding
import com.kma.movies.databinding.FragmentJobUserBinding
import com.kma.movies.model.JobResponse
import com.kma.movies.ui.home.master.ListJobAdapter
import com.kma.movies.ui.home.viewmodel.ListJobViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class ListJobByUserFragment : Fragment(R.layout.fragment_job_user), ListJobAdapter.OnItemClickListener {

    private val favoriteViewModel: ListJobViewModel by sharedViewModel()
    private val listJobAdapter: ListJobAdapter by inject()
    private val listJobNotAdapter: ListJobAdapter by inject()

    private var _binding: FragmentJobUserBinding? = null
    private val binding get() = _binding!!
    private var isEmpty1 = false
    private var isEmpty2 = false
    private val navArgs : ListJobByUserFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()
        setupSwipeRefresh()


        viewLifecycleOwner.lifecycleScope.launch {
            favoriteViewModel.listJobState.collect {
                handleFavoriteMoviesDataState(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            favoriteViewModel.listJobStateNew.collect { state ->
                when (state.status) {
                    Resource.Status.LOADING -> {
                    }
                    Resource.Status.SUCCESS -> {
                        if (state.data.isNullOrEmpty()) {
                            isEmpty1 = true
                            if (isEmpty2) {
                                binding.layoutEmpty.visible()
                                binding.llData.gone()
                            }
                        } else {

                            binding.layoutEmpty.gone()
                            binding.llData.visible()
                            loadMovies(state.data)
                        }
                    }
                    Resource.Status.ERROR -> {
                        binding.llData.gone()
                    }
                    Resource.Status.EMPTY -> {
                        Timber.d("Empty state.")
                    }
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        favoriteViewModel.disposable?.dispose()
    }
    private var listJobAll : List<JobResponse>? = listOf()
    private fun handleFavoriteMoviesDataState(state: Resource<List<JobResponse>>) {
        when (state.status) {
            Resource.Status.LOADING -> {
            }
            Resource.Status.SUCCESS -> {
                listJobAll =  emptyList()
                listJobAll = state.data
                if (state.data.isNullOrEmpty()) {
                    isEmpty2 = true
                    if (isEmpty1) {
                        binding.layoutEmpty.visible()
                        binding.llData.gone()
                    }
                } else {
                    binding.layoutEmpty.gone()
                    binding.llData.visible()
                    loadMoviesNot(state.data)
                }
            }
            Resource.Status.ERROR -> {
            }
            Resource.Status.EMPTY -> {
                Timber.d("Empty state.")
            }
        }
    }

    private fun loadMovies(movies: List<JobResponse>?) {
        movies?.let {
            listJobAdapter.clear()
            listJobAdapter.fillList(it)
        }
    }
    private fun loadMoviesNot(movies: List<JobResponse>?) {
        movies?.let {
            listJobNotAdapter.clear()
            listJobNotAdapter.fillList(it)
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.fetchListJobByUser(navArgs.username)
        favoriteViewModel.fetchListJobPosted(navArgs.username)

    }
    private fun setupRecyclerView() {
        listJobAdapter.setOnMovieClickListener(this)
        binding.srlFragmentMovieList.isEnabled = false
        binding.rvFragmentMovieList.adapter = listJobAdapter
        binding.rvFragmentMovieList.layoutManager = LinearLayoutManager(activity)

        listJobNotAdapter.setOnMovieClickListener(this)
        binding.rvFragmentMovieListNot.adapter = listJobNotAdapter
        binding.rvFragmentMovieListNot.layoutManager = LinearLayoutManager(activity)
    }

    private fun setupSwipeRefresh() {
        binding.srlFragmentMovieList.setOnRefreshListener {
            favoriteViewModel.fetchListJobApplied(navArgs.username)
        }
    }

    override fun onItemClick(jobResponse: JobResponse, container: View) {
        val action = ListJobAppliedFragmentDirections.navigateToMovieDetails(id = jobResponse.id!!, posterPath = jobResponse.image!!, isApply = true)
        findNavController().navigate(action)
    }


}