package com.akinci.repolisting.commons.components.list

class RecyclerViewClickListener( val clickListener : (position:Int,  data:Any) -> Unit) {
    fun onClick(position:Int, data:Any) = clickListener(position, data)
}