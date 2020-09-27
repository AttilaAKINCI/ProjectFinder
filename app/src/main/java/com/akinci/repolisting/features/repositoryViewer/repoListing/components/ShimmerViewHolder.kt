package com.akinci.repolisting.features.repositoryViewer.repoListing.components

import com.akinci.repolisting.commons.components.list.BaseViewHolder
import com.akinci.repolisting.commons.components.list.RecyclerViewClickListener
import com.akinci.repolisting.databinding.RowShimmerBinding
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.model.ShimmerRowModel

class ShimmerViewHolder constructor(val binding: RowShimmerBinding) : BaseViewHolder(binding.root){
    override fun bind(item : Any, position: Int, clickListener: RecyclerViewClickListener?) {
        if(item is ShimmerRowModel){
            binding.isStarVisible = item.isStarEnabled
            binding.executePendingBindings()

            binding.shimmerViewContainer.startShimmer()
        }
    }
}