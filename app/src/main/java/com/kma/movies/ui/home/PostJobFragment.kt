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
import com.kma.movies.databinding.FragmentPostJobBinding
import com.kma.movies.databinding.FragmentProfileBinding
import com.kma.movies.model.DataUserResponse
import com.kma.movies.model.Education
import com.kma.movies.model.Experience
import com.kma.movies.model.Job
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

class PostJobFragment : Fragment(R.layout.fragment_post_job) {

    private val postCvViewModel: PostJobViewModel by sharedViewModel()

    private var _binding: FragmentPostJobBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        viewLifecycleOwner.lifecycleScope.launch {
            postCvViewModel.postJobState.collect {
                when (it.status) {
                    Resource.Status.LOADING -> {
                    }

                    Resource.Status.SUCCESS -> {
                        Snackbar.make(binding.llContainer, "Success", Snackbar.LENGTH_LONG)
                            .setAnchorId(R.id.bottom_navigation).show()
                        findNavController().popBackStack()
                    }

                    Resource.Status.ERROR -> {
                        Snackbar.make(binding.llContainer, "Error" +it.message , Snackbar.LENGTH_LONG)
                            .setAnchorId(R.id.bottom_navigation).show()
                        findNavController().popBackStack()
                    }

                    Resource.Status.EMPTY -> {
                        Timber.d("Empty state.")
                    }
                }
            }
        }

    }

    private fun initView() {
        activity?.let {
            binding.spExp.adapter = ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.Exp)
            )
            binding.spExp.setSelection(0)
            binding.spTag.adapter = ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.Tag)
            )
            binding.spTag.setSelection(0)
        }
        binding.btnPostCV.setOnClickListener {

            if (binding.edtTitle.text.toString().length < 6) {
                Snackbar.make(binding.llContainer, "Vui lòng điền vị trí hơn 5 kí tự", Snackbar.LENGTH_LONG)
                    .setAnchorId(R.id.bottom_navigation).show()
                return@setOnClickListener
            }
            if (checkEmpty(arrayListOf(
                    binding.edtTitle.text.toString(),
                    binding.edtDes.text.toString(),
                    binding.spTag.selectedItem.toString(),
                    binding.edtAddress.text.toString(),
                    binding.edtResponsive.text.toString(),
                    binding.edtSalary.text.toString(),
                    binding.edtCompany.text.toString(),
                    binding.edtBenefit.text.toString(),
                    binding.spExp.selectedItem.toString(),
                    binding.edtDeadline.text.toString()
                    )
                )
            )
                return@setOnClickListener
            val data = Job(
                binding.edtTitle.text.toString(),
                binding.edtDes.text.toString(),
                binding.spTag.selectedItem.toString(),
                binding.edtAddress.text.toString(),
                binding.edtResponsive.text.toString(),
                binding.edtSalary.text.toString(),
                binding.edtCompany.text.toString(),
                binding.edtBenefit.text.toString(),
                binding.spExp.selectedItem.toString(),
                binding.edtDeadline.text.toString()
            )
            postCvViewModel.postData(data)
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


}