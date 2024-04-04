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
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kma.movies.R
import com.kma.movies.common.utils.gone
import com.kma.movies.common.utils.visible
import com.kma.movies.data.Resource
import com.kma.movies.databinding.FragmentHomeBinding
import com.kma.movies.model.JobResponse
import com.kma.movies.ui.home.master.ListJobAdapter
import com.kma.movies.ui.home.viewmodel.ListJobViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class ListJobByTagFragment : Fragment(R.layout.fragment_home), ListJobAdapter.OnItemClickListener {

    private val listJobViewModel: ListJobViewModel by sharedViewModel()
    private val listJobAdapter: ListJobAdapter by inject()
    private val args: ListJobByTagFragmentArgs by navArgs()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        setupSwipeRefresh()
        listJobViewModel.fetchListJobByTag(args.tag)
        viewLifecycleOwner.lifecycleScope.launch {
            listJobViewModel.listJobState.collect {
                handleFavoriteMoviesDataState(it)
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        listJobViewModel.disposable?.dispose()
    }

    private fun handleFavoriteMoviesDataState(state: Resource<List<JobResponse>>) {
        when (state.status) {
            Resource.Status.LOADING -> {
                binding.srlFragmentMovieList.isRefreshing = true
            }
            Resource.Status.SUCCESS -> {
                binding.srlFragmentMovieList.isRefreshing = false
                binding.layoutLogin.gone()
                binding.srlFragmentMovieList.visible()
                if (state.data.isNullOrEmpty()) {
                    binding.layoutEmpty.visible()
                    binding.rvFragmentMovieList.gone()
                } else {
                    binding.layoutEmpty.gone()
                    binding.rvFragmentMovieList.visible()
                    loadMovies(state.data)
                }
            }
            Resource.Status.ERROR -> {
                binding.srlFragmentMovieList.isRefreshing = false
                binding.layoutLogin.visible()
                binding.rvFragmentMovieList.gone()
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

    override fun onResume() {
        super.onResume()
    }
    private fun setupRecyclerView() {
        listJobAdapter.setOnMovieClickListener(this)
        binding.rvFragmentMovieList.adapter = listJobAdapter
        binding.rvFragmentMovieList.layoutManager = LinearLayoutManager(activity)
    }

    private fun setupSwipeRefresh() {
        binding.srlFragmentMovieList.setOnRefreshListener {
            listJobViewModel.fetchListJobByTag(args.tag)
        }
    }

    override fun onItemClick(jobResponse: JobResponse, container: View) {
        val action = ListJobFragmentDirections.navigateToMovieDetails(id = jobResponse.id!!, posterPath = jobResponse.image!!, isApply = false)
        findNavController().navigate(action)
    }


}