package com.kma.movies.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kma.movies.R
import com.kma.movies.common.utils.gone
import com.kma.movies.common.utils.visible
import com.kma.movies.data.Resource
import com.kma.movies.data.sources.remote.CommonSharedPreferences
import com.kma.movies.databinding.FragmentAccountBinding
import com.kma.movies.model.DataUserResponse
import com.kma.movies.ui.home.viewmodel.ProfileViewModel
import com.kma.movies.ui.sign_in.LogoutDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment(R.layout.fragment_account) {
    private val profileViewModel: ProfileViewModel by sharedViewModel()
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private fun initAction() {
        binding.buttonNavLogin.setOnClickListener {
            val action = AccountFragmentDirections.navigateToSignIn()
            findNavController().navigate(action)
        }
        binding.textSignOut.setOnClickListener {
            activity?.supportFragmentManager?.let { it1 ->
                LogoutDialog.newInstance {
                    CommonSharedPreferences.getInstance().remoCurrrentName()
                    profileViewModel.getData()
                }.show(it1, " dialog")
            }
        }
        binding.tvProfile.setOnClickListener {
            val action =
                AccountFragmentDirections.navigateToProfile(binding.textUsername.text.toString())
            findNavController().navigate(action)
        }
        binding.tvListJobApply.setOnClickListener {
            val action =
                AccountFragmentDirections.navigateToJobApplied(binding.textUsername.text.toString())
            findNavController().navigate(action)
        }
        binding.tvPostJob.setOnClickListener {
            val action =
                AccountFragmentDirections.navigateToPostJob()
            findNavController().navigate(action)
        }
        binding.tvJobPosted.setOnClickListener {
            val action =
                AccountFragmentDirections.navigateToJobUser(binding.textUsername.text.toString())
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
        initData()
    }

    private fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.profileState.collect {
                handleDataState(it)
            }
        }

        profileViewModel.getData()
    }

    private fun handleDataState(state: Resource<List<DataUserResponse>>) {
        when (state.status) {
            Resource.Status.LOADING -> {

            }

            Resource.Status.SUCCESS -> {
                if (state.data?.size == 1) {
                    setDataUser(state.data[0])
                    binding.containedProfile.visible()
                    binding.layoutLogin.gone()
                } else {
                    CommonSharedPreferences.getInstance().remoCurrrentName()
                    binding.containedProfile.gone()
                    binding.layoutLogin.visible()
                }

            }

            Resource.Status.ERROR -> {
                CommonSharedPreferences.getInstance().remoCurrrentName()
                binding.containedProfile.gone()
                binding.layoutLogin.visible()
            }

            Resource.Status.EMPTY -> {
                Timber.d("Empty state.")
            }
        }
    }

    private fun setDataUser(data: DataUserResponse?) {
        binding.textNameActor.text = data?.name
        binding.textUsername.text = data?.username
        if (data?.role == "employee") {
            binding.tvListJobApply.visible()
            binding.tvProfile.visible()
            binding.tvPostJob.gone()
            binding.tvJobPosted.gone()
        } else {
            binding.tvListJobApply.gone()
            binding.tvProfile.gone()
            binding.tvPostJob.visible()
            binding.tvJobPosted.visible()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        fun newInstance(): AccountFragment {
            val fragment = AccountFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}