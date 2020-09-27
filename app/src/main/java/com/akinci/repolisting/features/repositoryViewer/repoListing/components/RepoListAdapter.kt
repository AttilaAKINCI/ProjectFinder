package com.akinci.repolisting.features.repositoryViewer.repoListing.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akinci.repolisting.commons.components.list.BaseViewHolder
import com.akinci.repolisting.commons.components.list.RecyclerViewClickListener
import com.akinci.repolisting.commons.data.model.ListRowDataContract
import com.akinci.repolisting.databinding.RowRepoBinding
import com.akinci.repolisting.databinding.RowShimmerBinding
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.model.RepoRowModel

class RepoListAdapter(private val clickListener: RecyclerViewClickListener) :
    ListAdapter<ListRowDataContract, RecyclerView.ViewHolder>(DiffCallBack()) {

    private val REPO_ROW = 0
    private val SHIMMER_ROW = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType){
            REPO_ROW -> RepoViewHolder(RowRepoBinding.inflate(layoutInflater, parent, false))
            SHIMMER_ROW -> ShimmerViewHolder(RowShimmerBinding.inflate(layoutInflater, parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is RepoRowModel -> REPO_ROW
            else -> SHIMMER_ROW
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if(holder is BaseViewHolder){ holder.bind(item, position, clickListener) }
    }
}

class DiffCallBack : DiffUtil.ItemCallback<ListRowDataContract>() {
    override fun areItemsTheSame(oldItem: ListRowDataContract, newItem: ListRowDataContract): Boolean {
        if(oldItem is RepoRowModel && newItem is RepoRowModel){ return oldItem.id == newItem.id }
        return true
    }

    override fun areContentsTheSame(oldItem: ListRowDataContract, newItem: ListRowDataContract): Boolean {
        if(oldItem is RepoRowModel && newItem is RepoRowModel){ return oldItem == newItem }
        return true
    }
}