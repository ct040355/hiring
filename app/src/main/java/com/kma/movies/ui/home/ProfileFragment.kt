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
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import androidx.core.util.Pair as UtilPair
import com.kma.movies.R
import com.kma.movies.common.utils.gone
import com.kma.movies.common.utils.invisible
import com.kma.movies.common.utils.setAnchorId
import com.kma.movies.common.utils.visible
import com.kma.movies.data.Resource
import com.kma.movies.databinding.FragmentHomeBinding
import com.kma.movies.databinding.FragmentProfileBinding
import com.kma.movies.model.CvResponse
import com.kma.movies.model.Education
import com.kma.movies.model.Experience
import com.kma.movies.model.RegisterStatus
import com.kma.movies.ui.home.master.CategoryListAdapter
import com.kma.movies.ui.home.master.EducationAdapter
import com.kma.movies.ui.home.master.ExperienceAdapter
import com.kma.movies.ui.home.viewmodel.CategoryViewModel
import com.kma.movies.ui.home.viewmodel.GetCvViewModel
import com.kma.movies.ui.home.viewmodel.PostCvViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val getCvViewModel: GetCvViewModel by sharedViewModel()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val navArgs: ProfileFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initAction()

        viewLifecycleOwner.lifecycleScope.launch {
            getCvViewModel.profileState.collect {
                handleFavoriteMoviesDataState(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            getCvViewModel.deleteState.collect { state ->
                when (state.status) {
                    Resource.Status.LOADING -> {
                    }

                    Resource.Status.SUCCESS -> {
                        getCvViewModel.getCv(navArgs.username)
                    }

                    Resource.Status.ERROR -> {
                        Snackbar.make(binding.root, "Something error", Snackbar.LENGTH_LONG)
                            .setAnchorId(R.id.bottom_navigation).show()
                    }

                    Resource.Status.EMPTY -> {
                        Timber.d("Empty state.")
                    }
                }
            }
        }
        getCvViewModel.getCv(navArgs.username)
    }

    private fun initAction() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.navigateToPostCV())
        }
        binding.btnRemoveCV.setOnClickListener {
            getCvViewModel.deleteCV(navArgs.username)
        }
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
                binding.llContainer.gone()
            }

            Resource.Status.EMPTY -> {
                Timber.d("Empty state.")
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
            binding.rcvEducation.layoutManager = LinearLayoutManager(activity)
            binding.rcvEducation.adapter = EducationAdapter(activity, objectList)
            val experienceList = gson.fromJson(it.experience, Array<Experience>::class.java).asList()
            binding.rcvWork.layoutManager = LinearLayoutManager(activity)
            binding.rcvWork.adapter = ExperienceAdapter(activity, experienceList)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}