package com.kma.movies.ui.home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kma.movies.R
import com.kma.movies.model.Category


class CategoryViewModel() : ViewModel() {

    fun listCategory(context : Context) : MutableList<Category> {
        return mutableListOf(
            Category(context.getString(R.string.tag_1), R.drawable.ic_cntt),
            Category(context.getString(R.string.tag_2), R.drawable.ic_kt_tc),
            Category(context.getString(R.string.tag_3), R.drawable.ic_education),
            Category(context.getString(R.string.tag_4), R.drawable.ic_digital),
            Category(context.getString(R.string.tag_5), R.drawable.ic_telecommunication),
            Category(context.getString(R.string.tag_6), R.drawable.ic_sale),
            Category(context.getString(R.string.tag_7), R.drawable.ic_content),
            Category(context.getString(R.string.tag_8), R.drawable.ic_healthcare),
            Category(context.getString(R.string.tag_9), R.drawable.ic_logistics),
            Category(context.getString(R.string.tag_10), R.drawable.ic_build),
            Category(context.getString(R.string.tag_11), R.drawable.ic_factory),
            Category(context.getString(R.string.tag_12), R.drawable.ic_other),
        )
    }


}