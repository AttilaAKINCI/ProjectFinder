package com.akinci.projectfinder.features.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akinci.projectfinder.databinding.RowReposBinding
import com.akinci.projectfinder.features.repocommon.data.output.RepoResponse

class RepoListAdapter(private val clickListener: (Int) -> Unit) : ListAdapter<RepoResponse, RepoListAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(RowReposBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position, clickListener)
    }

    class NewsViewHolder(val binding: RowReposBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RepoResponse, position : Int , clickListener: (Int) -> Unit) {
            // fill row instances..
            binding.repoCardView.setOnClickListener { clickListener.invoke(position) }
//            binding.data = item
//
//            binding.newsImage.setGlideImageCentered(item.picUrl)

            binding.executePendingBindings()
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<RepoResponse>() {
        override fun areItemsTheSame(oldItem: RepoResponse, newItem: RepoResponse): Boolean { return oldItem.id == newItem.id }
        override fun areContentsTheSame(oldItem: RepoResponse, newItem: RepoResponse): Boolean { return oldItem == newItem }
    }
}

