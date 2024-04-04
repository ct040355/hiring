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
import com.kma.movies.databinding.ItemJobBinding
import com.kma.movies.databinding.RowMovieListBinding
import com.kma.movies.model.Category
import com.kma.movies.model.JobResponse
import com.kma.movies.model.Movie
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class ListJobAdapter(val context: Context?, var items: List<JobResponse> = ArrayList()) :
    RecyclerView.Adapter<ListJobViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(jobResponse: JobResponse, container: View) // pass ImageView to make shared transition
    }

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListJobViewHolder {
        return ListJobViewHolder(
            ItemJobBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListJobViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvCompany.text = item.company
        holder.tvAddress.text = item.address
        holder.tvSalary.text = item.salary
        holder.tvDate.text = item.deadline
        holder.tvTag.text = item.tag
        holder.imageView.load(url = ApiClient.POSTER_BASE_URL + item.image,)
        holder.imageView.load(url = ApiClient.POSTER_BASE_URL + item.image,
            crossFade = true, width = 160.dp, height = 160.dp) { color ->

        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item, holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnMovieClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun fillList(items: List<JobResponse>) {
        this.items += items
        notifyDataSetChanged()
    }

    fun clear() {
        this.items = emptyList()
    }


}

class ListJobViewHolder(binding: ItemJobBinding) : RecyclerView.ViewHolder(binding.root) {
    val tvTitle: TextView = binding.tvTitle
    val tvCompany: TextView = binding.tvCompany
    val tvAddress: TextView = binding.tvAddress
    val tvSalary: TextView = binding.tvSalary
    val tvDate: TextView = binding.tvDate
    val tvTag: TextView = binding.tvTag
    val imageView = binding.imageView
}