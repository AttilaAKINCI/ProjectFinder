package com.akinci.projectfinder.common.extension

import android.graphics.Shader
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.akinci.projectfinder.common.component.TileDrawable

/*********************************** Tiled background related  ***********************************/
fun ImageView.setTiledImageDrawable(tiledPattern: Int) {
    val backgroundDrawable = ContextCompat.getDrawable(context, tiledPattern)
    setImageDrawable(TileDrawable(backgroundDrawable!!, Shader.TileMode.REPEAT))
}
/*************************************************************************************************/

