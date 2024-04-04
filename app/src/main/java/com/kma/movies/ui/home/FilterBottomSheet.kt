package com.kma.movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kma.movies.R
import com.kma.movies.databinding.DialogAvailableFileBinding


class FilterBottomSheet : BottomSheetDialogFragment() {
    private lateinit var mBinding: DialogAvailableFileBinding
    private var actionDone: ((String, String) -> Unit)? = null
    private var  tagNew : String? = null
    private var  locate : String? = null


    companion object {
        fun newInstance(
            tag : String,
            locate : String,
            actionDone: ((String, String) -> Unit)? = null
        ): FilterBottomSheet {
            val args = Bundle()
            val fragment = FilterBottomSheet()
            fragment.arguments = args
            fragment.tagNew = tag
            fragment.locate = locate
            fragment.actionDone = actionDone
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogAvailableFileBinding.inflate(layoutInflater)
        initAction()
        return mBinding.root
    }

    private fun initAction() {
        mBinding.edtTag.setText(tagNew)
        mBinding.edtLocate.setText(locate)
        mBinding.btnSave.setOnClickListener {
            actionDone?.invoke(mBinding.edtTag.text.toString(), mBinding.edtLocate.text.toString())
            dismissAllowingStateLoss()
        }
    }


}
