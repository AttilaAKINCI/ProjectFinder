package com.akinci.projectfinder.common.component

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class SnackBar {
    companion object{
        const val LENGTH_SHORT = Snackbar.LENGTH_SHORT
        const val LENGTH_LONG = Snackbar.LENGTH_LONG

        fun make(view : View, text: CharSequence, duration : Int) : Snackbar {
            // log every snackBar messages
            Timber.d(text.toString())
            return Snackbar.make(view, text, duration)
        }
        fun makeLarge(view : View, text: CharSequence, duration : Int) : Snackbar {
            return make(view, text, duration).apply {
                this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 8
            }
        }
    }
}