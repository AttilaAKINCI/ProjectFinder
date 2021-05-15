package com.akinci.projectfinder.features.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akinci.projectfinder.R
import com.akinci.projectfinder.common.extension.setGlideImageCentered
import com.akinci.projectfinder.databinding.RowReposBinding
import com.akinci.projectfinder.features.repocommon.data.output.RepoResponse

class RepoListAdapter(private val clickListener: (Int) -> Unit) : ListAdapter<RepoResponse, RepoListAdapter.RepoViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RepoViewHolder(RowReposBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position, clickListener)
    }

    class RepoViewHolder(val binding: RowReposBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RepoResponse, position : Int , clickListener: (Int) -> Unit) {
            // fill row instances..
            binding.repoCardView.setOnClickListener { clickListener.invoke(position) }
            binding.data = item

            item.owner?.avatar_url?.let {
                binding.repoImage.setGlideImageCentered(imageUrl = it, fallbackDrawableId = R.drawable.ic_person)
            }

            binding.executePendingBindings()
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<RepoResponse>() {
        override fun areItemsTheSame(oldItem: RepoResponse, newItem: RepoResponse): Boolean { return oldItem.id == newItem.id }
        override fun areContentsTheSame(oldItem: RepoResponse, newItem: RepoResponse): Boolean { return oldItem == newItem }
    }
}

