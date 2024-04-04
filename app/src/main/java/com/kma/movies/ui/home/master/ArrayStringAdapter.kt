package com.kma.movies.ui.home.master

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kma.movies.R
import com.kma.movies.common.glide.load
import com.kma.movies.common.utils.dp
import com.kma.movies.data.sources.remote.api.ApiClient
import com.kma.movies.databinding.ItemCategoryBinding
import com.kma.movies.databinding.ItemContentBinding
import com.kma.movies.databinding.RowMovieListBinding
import com.kma.movies.model.Category
import com.kma.movies.model.Movie
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class ArrayStringAdapter(val context: Context?, var items: List<String> = ArrayList()) :
    RecyclerView.Adapter<ArrayStringViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArrayStringViewHolder {
        return ArrayStringViewHolder(
            ItemContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArrayStringViewHolder, position: Int) {
        val item = items[position]
        holder.tvContent.text = item

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun fillList(items: List<String>) {
        clear()
        this.items += items
        notifyDataSetChanged()
    }

    fun clear() {
        this.items = emptyList()
    }


}

class ArrayStringViewHolder(binding: ItemContentBinding) : RecyclerView.ViewHolder(binding.root) {
    val tvContent: TextView = binding.tvContent
}