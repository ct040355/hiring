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
import com.kma.movies.databinding.ItemEducationBinding
import com.kma.movies.databinding.ItemExperienceBinding
import com.kma.movies.databinding.RowMovieListBinding
import com.kma.movies.model.Category
import com.kma.movies.model.Education
import com.kma.movies.model.Experience
import com.kma.movies.model.Movie
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class ExperienceAdapter(val context: Context?, var items: List<Experience> = ArrayList()) :
    RecyclerView.Adapter<ExperienceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceHolder {
        return ExperienceHolder(
            ItemExperienceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExperienceHolder, position: Int) {
        val item = items[position]
        holder.tvQuality.text = item.pos
        holder.tvDes.text = item.des
        holder.tvFrom.text = item.from
        holder.tvTo.text = item.to
        holder.tvSchool.text = item.com

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun fillList(items: List<Experience>) {
        clear()
        this.items += items
        notifyDataSetChanged()
    }

    fun clear() {
        this.items = emptyList()
    }


}

class ExperienceHolder(binding: ItemExperienceBinding) : RecyclerView.ViewHolder(binding.root) {
    val tvQuality: TextView = binding.tvQuality
    val tvDes: TextView = binding.tvDes
    val tvFrom: TextView = binding.tvFrom
    val tvTo: TextView = binding.tvTo
    val tvSchool: TextView = binding.tvSchool


}