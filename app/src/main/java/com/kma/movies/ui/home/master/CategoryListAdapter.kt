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
import com.kma.movies.databinding.RowMovieListBinding
import com.kma.movies.model.Category
import com.kma.movies.model.Movie
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class CategoryListAdapter(val context: Context?, var items: List<Category> = ArrayList()) :
    RecyclerView.Adapter<CategoryViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(movie: Category) // pass ImageView to make shared transition
    }

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = items[position]
        holder.tvName.text = category.name
        Glide.with(holder.itemView.context)
            .load(category.icon)
            .into(holder.imageView)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(category)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnMovieClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun fillList(items: List<Category>) {
        clear()
        this.items += items
        notifyDataSetChanged()
    }

    fun clear() {
        this.items = emptyList()
    }


}

class CategoryViewHolder(binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
    val tvName: TextView = binding.tvName
    val imageView = binding.imageView
}