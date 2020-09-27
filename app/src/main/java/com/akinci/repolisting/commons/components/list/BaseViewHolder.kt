package com.akinci.repolisting.commons.components.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /** On RecyclerView onBindViewHolder action every ViewHolder handles its binding
     *  using this function
     * **/
    abstract fun bind(item: Any, position : Int, clickListener: RecyclerViewClickListener? = null)
}