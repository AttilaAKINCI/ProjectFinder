package com.akinci.repolisting.features.repositoryViewer.repoListing.components

import com.akinci.repolisting.commons.components.list.BaseViewHolder
import com.akinci.repolisting.commons.components.list.RecyclerViewClickListener
import com.akinci.repolisting.databinding.RowRepoBinding
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.model.RepoRowModel

class RepoViewHolder constructor(val binding: RowRepoBinding) : BaseViewHolder(binding.root){

    override fun bind(item : Any, position: Int, clickListener: RecyclerViewClickListener?) {
        if(item is RepoRowModel){

            clickListener?.let { binding.clickListener = it }
            binding.position = position
            binding.data = item
            binding.executePendingBindings()
        }
    }
}
