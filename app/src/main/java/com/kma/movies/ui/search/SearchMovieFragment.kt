package com.kma.movies.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kma.movies.R
import com.kma.movies.common.recyclerview.PaginationScrollListener
import com.kma.movies.common.utils.gone
import com.kma.movies.common.utils.setAnchorId
import com.kma.movies.common.utils.visible
import com.kma.movies.data.Resource
import com.kma.movies.databinding.FragmentSearchViewBinding
import com.kma.movies.model.JobResponse
import com.kma.movies.model.Movie
import com.kma.movies.ui.home.FilterBottomSheet
import com.kma.movies.ui.home.ListJobFragmentDirections
import com.kma.movies.ui.home.master.ListJobAdapter
import com.kma.movies.ui.home.master.MovieListAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class SearchMovieFragment : Fragment(R.layout.fragment_search_view),
    ListJobAdapter.OnItemClickListener {

    private val searchJobViewModel: SearchJobViewModel by sharedViewModel()
    private val listJobAdapter: ListJobAdapter by inject()

    private var _binding: FragmentSearchViewBinding? = null
    private val binding get() = _binding!!
    private var localDefault = "Tất cả"
    private var tagDefault = "Tất cả"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imvBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            searchJobViewModel.searchMoviesState.collect {
                handleMoviesDataState(it)
            }
        }
        binding.idSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrBlank()) {

                } else {
                    searchJobViewModel.fetchSearchJob(query, if (tagDefault == "Tất cả") "" else tagDefault, if (localDefault == "Tất cả") "" else localDefault)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
        initAction()
    }

    private fun initAction() {
        binding.imvFilter.setOnClickListener {
            activity?.supportFragmentManager?.let { it1 ->
                FilterBottomSheet.newInstance(tagDefault, localDefault){ tag, locate ->
                    localDefault = locate
                    tagDefault =  tag
                }.show(it1, "taggg")
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        searchJobViewModel.disposable?.dispose()
    }

    private fun handleMoviesDataState(state: Resource<List<JobResponse>>) {
        when (state.status) {
            Resource.Status.LOADING -> {
                binding.srlFragmentMovieList.isRefreshing = true
            }

            Resource.Status.SUCCESS -> {
                binding.srlFragmentMovieList.isRefreshing = false
                loadMovies(state.data)
            }

            Resource.Status.ERROR -> {
                binding.srlFragmentMovieList.isRefreshing = false
                binding.pbFragmentMovieList.gone()
                Snackbar.make(
                    binding.srlFragmentMovieList,
                    getString(R.string.error_message_pattern, state.message),
                    Snackbar.LENGTH_LONG
                )
                    .setAnchorId(R.id.bottom_navigation).show()
            }

            Resource.Status.EMPTY -> {
                Timber.d("Empty state.")
            }
        }
    }

    private fun loadMovies(jobResponse: List<JobResponse>?) {
        listJobAdapter.clear()
        if (jobResponse != null) {
            listJobAdapter.fillList(jobResponse)
        }
    }

    private fun setupRecyclerView() {
        binding.srlFragmentMovieList.isEnabled = false
        listJobAdapter.setOnMovieClickListener(this)
        binding.rvFragmentMovieList.adapter = listJobAdapter
        binding.rvFragmentMovieList.layoutManager = LinearLayoutManager(activity)

    }

    override fun onItemClick(jobResponse: JobResponse, container: View) {
        val action = ListJobFragmentDirections.navigateToMovieDetails(id = jobResponse.id!!, posterPath = jobResponse.image!!, isApply = false)
        findNavController().navigate(action)
    }

}