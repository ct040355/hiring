package com.kma.movies.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import androidx.core.util.Pair as UtilPair
import com.kma.movies.R
import com.kma.movies.common.utils.gone
import com.kma.movies.common.utils.setAnchorId
import com.kma.movies.common.utils.visible
import com.kma.movies.data.Resource
import com.kma.movies.databinding.FragmentHomeBinding
import com.kma.movies.databinding.FragmentPostCvBinding
import com.kma.movies.databinding.FragmentProfileBinding
import com.kma.movies.model.DataUserResponse
import com.kma.movies.model.Education
import com.kma.movies.model.Experience
import com.kma.movies.model.JobResponse
import com.kma.movies.model.RegisterStatus
import com.kma.movies.model.UpCV
import com.kma.movies.ui.home.master.CategoryListAdapter
import com.kma.movies.ui.home.viewmodel.CategoryViewModel
import com.kma.movies.ui.home.viewmodel.ListJobViewModel
import com.kma.movies.ui.home.viewmodel.PostCvViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class PostCVFragment : Fragment(R.layout.fragment_post_cv) {

    private val postCvViewModel: PostCvViewModel by sharedViewModel()

    private var _binding: FragmentPostCvBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostCvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        viewLifecycleOwner.lifecycleScope.launch {
            postCvViewModel.profileState.collect {
                handleFavoriteMoviesDataState(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            postCvViewModel.profileStateAccount.collect {
                handleDataState(it)
            }
        }
        postCvViewModel.getCurrent()

    }

    private var currentName: String? = null
    private var name: String? = null
    private fun handleDataState(state: Resource<List<DataUserResponse>>) {
        when (state.status) {
            Resource.Status.LOADING -> {

            }

            Resource.Status.SUCCESS -> {
                currentName = state.data?.get(0)?.username
                name = state.data?.get(0)?.name
            }

            Resource.Status.ERROR -> {

            }

            Resource.Status.EMPTY -> {
                Timber.d("Empty state.")
            }
        }
    }

    private fun initView() {
        activity?.let {
            binding.spType.adapter = ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.Type)
            )
            binding.spType.setSelection(0)
            binding.spExp.adapter = ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.Exp)
            )
            binding.spExp.setSelection(0)
            binding.spGen.adapter = ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.Gen)
            )
            binding.spGen.setSelection(0)
            binding.spTag.adapter = ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.Tag)
            )
            binding.spTag.setSelection(0)
            binding.spQuality.adapter = ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.Quality)
            )
            binding.spQuality.setSelection(0)
        }

        initEducation()
        initWork()
        binding.btnPostCV.setOnClickListener {
            if (checkEmpty(arrayListOf(
                        currentName,
                        name,
                        binding.edtDescription.text.toString(),
                        binding.edtSalary.text.toString(),
                        binding.edtAddress.text.toString(),
                        binding.spGen.selectedItem.toString(),
                        binding.spType.selectedItem.toString(),
                        binding.spQuality.selectedItem.toString(),
                        binding.spExp.selectedItem.toString()
                    )
                )
            )
                return@setOnClickListener
            val data = UpCV(
                currentName,
                binding.edtName.text.toString(),
                binding.edtDescription.text.toString(),
                listEducation,
                listExperience,
                binding.edtSalary.text.toString(),
                binding.edtAddress.text.toString(),
                binding.spGen.selectedItem.toString(),
                binding.spType.selectedItem.toString(),
                binding.spQuality.selectedItem.toString(),
                binding.spExp.selectedItem.toString(),
                binding.spTag.selectedItem.toString()
            )
            postCvViewModel.postData(data)
        }

    }

    private var listExperience: ArrayList<Experience>? = arrayListOf()

    private fun initWork() {
        binding.btnWorkAdd.setOnClickListener {
            if (checkEmpty(
                    arrayListOf(
                        binding.edtEducationQua.text.toString(),
                        binding.edtEducationStartTime.text.toString(),
                        binding.edtEducationEndTime.text.toString(),
                        binding.edtEducationSchool.text.toString(),
                        binding.edtEducationSchool.text.toString(),
                    )
                )
            )
                return@setOnClickListener
            val edu = Experience(
                binding.edtWorkLocate.text.toString(),
                binding.edtWorkStartTime.text.toString(),
                binding.edtWorkDes.text.toString(),
                binding.edtWorkCompany.text.toString(),
                binding.edtWorkEndTime.text.toString(),
            )
            listExperience?.add(edu)
            binding.tvWorkSize.text = "Đã thêm " + listExperience?.size.toString()
        }
    }

    private var listEducation: ArrayList<Education>? = arrayListOf()
    private fun initEducation() {
        binding.btnEduAdd.setOnClickListener {
            if (checkEmpty(
                    arrayListOf(
                        binding.edtEducationQua.text.toString(),
                        binding.edtEducationStartTime.text.toString(),
                        binding.edtEducationEndTime.text.toString(),
                        binding.edtEducationSchool.text.toString(),
                        binding.edtEducationSchool.text.toString(),
                    )
                )
            )
                return@setOnClickListener
            val edu = Education(
                binding.edtEducationQua.text.toString(),
                binding.edtEducationStartTime.text.toString(),
                binding.edtEducationEndTime.text.toString(),
                binding.edtEducationSchool.text.toString(),
                binding.edtEducationSchool.text.toString(),
            )
            listEducation?.add(edu)
            binding.tvEducationSize.text = "Đã thêm " + listEducation?.size.toString()
        }
    }

    private fun checkEmpty(data: ArrayList<String?>): Boolean {
        data.forEach { it ->
            if (it.isNullOrEmpty()) {
                Snackbar.make(binding.llContainer, "Vui lòng điền form", Snackbar.LENGTH_LONG)
                    .setAnchorId(R.id.bottom_navigation).show()
                return true
            }
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun handleFavoriteMoviesDataState(state: Resource<RegisterStatus>) {
        when (state.status) {
            Resource.Status.LOADING -> {
            }

            Resource.Status.SUCCESS -> {
                Snackbar.make(binding.llContainer, "Success", Snackbar.LENGTH_LONG)
                    .setAnchorId(R.id.bottom_navigation).show()
                findNavController().popBackStack()
            }

            Resource.Status.ERROR -> {
                Snackbar.make(binding.llContainer, "Error" +state.message , Snackbar.LENGTH_LONG)
                    .setAnchorId(R.id.bottom_navigation).show()
                findNavController().popBackStack()
            }

            Resource.Status.EMPTY -> {
                Timber.d("Empty state.")
            }
        }
    }


}