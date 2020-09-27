package com.akinci.repolisting.commons.components.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.akinci.repolisting.R

class ImagedToolbar : Toolbar {
    constructor(context: Context) : super(context) { initialize(context, null, 0) }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { initialize(
        context,
        attrs,
        0
    ) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) { initialize(context, attrs, defStyleAttr) }

    lateinit var container:RelativeLayout
    lateinit var leftBarContainer:LinearLayout
    lateinit var titleTextView:AppCompatTextView
    lateinit var rightBarContainer:LinearLayout
    lateinit var clickListener: OnClickListener

    private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int){

        container = RelativeLayout(context)
        container.layoutParams = ViewGroup.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        )

        leftBarContainer = LinearLayout(context)
        leftBarContainer.orientation = LinearLayout.HORIZONTAL
        val leftBarLayoutParams = RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        )
        leftBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START)
        leftBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        leftBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        leftBarContainer.layoutParams = leftBarLayoutParams

        titleTextView = AppCompatTextView(context)
        titleTextView.isSingleLine = true
        titleTextView.ellipsize = TextUtils.TruncateAt.END
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        titleTextView.setTextColor(ResourcesCompat.getColor(resources, R.color.colorSoftWhite, context.theme))
        val titleLayoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        titleLayoutParams.gravity = Gravity.START or Gravity.CENTER_VERTICAL
        titleTextView.layoutParams = titleLayoutParams

        leftBarContainer.addView(titleTextView)

        rightBarContainer = LinearLayout(context)
        rightBarContainer.orientation = LinearLayout.HORIZONTAL
        val rightBarLayoutParams = RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        )
        rightBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END)
        rightBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        rightBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        rightBarContainer.layoutParams = rightBarLayoutParams

        container.addView(leftBarContainer)
        container.addView(rightBarContainer)
        addView(container)
    }

    override fun setTitle(title: CharSequence?) { titleTextView.text = title }
    fun resetBarItems(){ rightBarContainer.removeAllViews() }
    fun setRightBarIcon(drawable: Drawable){
        resetBarItems()
        var imageView = ImageView(context)
        imageView.background = drawable

        val imageViewLayoutParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        imageViewLayoutParams.gravity = Gravity.END or Gravity.CENTER_VERTICAL
        imageViewLayoutParams.marginEnd = resources.getDimensionPixelSize(R.dimen.imaged_toolbar_margin_end)
        imageView.layoutParams = imageViewLayoutParams

        clickListener?.let {
            imageView.setOnClickListener(it)
        }

        rightBarContainer.addView(imageView)
    }

}