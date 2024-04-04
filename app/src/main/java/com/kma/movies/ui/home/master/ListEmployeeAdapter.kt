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
import com.kma.movies.databinding.ItemEmployeeBinding
import com.kma.movies.databinding.ItemJobBinding
import com.kma.movies.databinding.RowMovieListBinding
import com.kma.movies.model.Category
import com.kma.movies.model.CvResponse
import com.kma.movies.model.JobResponse
import com.kma.movies.model.Movie
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class ListEmployeeAdapter(val context: Context?, var items: List<CvResponse> = ArrayList()) :
    RecyclerView.Adapter<ListEmployeeViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(jobResponse: CvResponse) // pass ImageView to make shared transition
    }

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListEmployeeViewHolder {
        return ListEmployeeViewHolder(
            ItemEmployeeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListEmployeeViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.name
        holder.tvTag.text = item.tag
        holder.tvAddress.text = item.location
        holder.imageView.load(url = ApiClient.POSTER_BASE_URL + item.image,
            crossFade = true, width = 160.dp, height = 160.dp) { color ->

        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnMovieClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun fillList(items: List<CvResponse>) {
        this.items += items
        notifyDataSetChanged()
    }

    fun clear() {
        this.items = emptyList()
    }


}

class ListEmployeeViewHolder(binding: ItemEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {
    val tvTitle: TextView = binding.tvname
    val tvTag: TextView = binding.tvTag
    val tvAddress: TextView = binding.tvAddress
    val imageView = binding.imageView
}