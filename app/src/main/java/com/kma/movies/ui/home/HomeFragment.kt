package com.kma.movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.util.Pair as UtilPair
import com.kma.movies.R
import com.kma.movies.common.utils.gone
import com.kma.movies.common.utils.visible
import com.kma.movies.data.Resource
import com.kma.movies.databinding.FragmentHomeBinding
import com.kma.movies.databinding.FragmentHomeNewBinding
import com.kma.movies.model.JobResponse
import com.kma.movies.ui.home.master.CategoryListAdapter
import com.kma.movies.ui.home.master.ListJobAdapter
import com.kma.movies.ui.home.viewmodel.CategoryViewModel
import com.kma.movies.ui.home.viewmodel.ListJobViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class HomeFragment : Fragment(R.layout.fragment_home_new), CategoryListAdapter.OnItemClickListener {

    private val listJobViewModel: ListJobViewModel by sharedViewModel()
    private val listJobAdapter: ListJobAdapter by inject()
    private val categoryListAdapter: CategoryListAdapter by inject()

    private var _binding: FragmentHomeNewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()
        setupRecyclerView2()
        viewLifecycleOwner.lifecycleScope.launch {
            listJobViewModel.listJobState.collect {
                handleFavoriteMoviesDataState(it)
            }
        }
        listJobViewModel.fetchListJobByTag(currentTag)
        binding.llSearch.setOnClickListener {
            val action = HomeFragmentDirections.navigateToSearch()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


    private fun setupRecyclerView() {
        binding.srlFragmentMovieList.isEnabled = false
        categoryListAdapter.setOnMovieClickListener(this)
        binding.rcvTag.adapter = categoryListAdapter
        binding.rcvTag.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        activity?.let { listJobViewModel.listCategory(it) }
            ?.let { categoryListAdapter.fillList(it) }
    }
    private fun handleFavoriteMoviesDataState(state: Resource<List<JobResponse>>) {
        when (state.status) {
            Resource.Status.LOADING -> {
                binding.srlFragmentMovieList.isRefreshing = true
            }
            Resource.Status.SUCCESS -> {
                binding.srlFragmentMovieList.isRefreshing = false
                binding.srlFragmentMovieList.visible()
                if (state.data.isNullOrEmpty()) {
                    binding.layoutEmpty.visible()
                    binding.rvFragmentMovieList.gone()
                } else {
                    binding.layoutEmpty.gone()
                    binding.rvFragmentMovieList.visible()
                    loadMovies(state.data.reversed())
                }
            }
            Resource.Status.ERROR -> {
                binding.srlFragmentMovieList.isRefreshing = false
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

    private fun setupRecyclerView2() {
        listJobAdapter.setOnMovieClickListener(object : ListJobAdapter.OnItemClickListener{
            override fun onItemClick(jobResponse: JobResponse, container: View) {
                val action = HomeFragmentDirections.navigateToMovieDetails(id = jobResponse.id!!, posterPath = jobResponse.image!!, isApply = false)
                findNavController().navigate(action)
            }
        })
        binding.rvFragmentMovieList.adapter = listJobAdapter
        binding.rvFragmentMovieList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }
    private var currentTag = ""
    override fun onItemClick(item: com.kma.movies.model.Category) {
        currentTag = item.name
        listJobViewModel.fetchListJobByTag(item.name)
    }
}