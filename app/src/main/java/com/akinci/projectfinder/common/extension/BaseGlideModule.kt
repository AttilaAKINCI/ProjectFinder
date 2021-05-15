package com.akinci.projectfinder.common.extension

import android.content.Context
import android.widget.ImageView
import com.akinci.projectfinder.R
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey

@GlideModule
class BaseGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.apply { RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(
            ObjectKey(
                System.currentTimeMillis().toShort()
            )
        ) }
    }
}

// Glide image loading with extension function
fun ImageView.setGlideImageCentered(imageUrl: String, fallbackDrawableId: Int = -1, cornerRadius: Int = -1){
    var request = GlideApp.with(context)
                    .load(imageUrl)
                    .centerCrop()

    if(fallbackDrawableId != -1){
        request = request.error(fallbackDrawableId)
                         .placeholder(fallbackDrawableId)
    }

    if(cornerRadius != -1){
        request = request.apply(RequestOptions.bitmapTransform(RoundedCorners(cornerRadius)))
    }

    request.into(this)
}

