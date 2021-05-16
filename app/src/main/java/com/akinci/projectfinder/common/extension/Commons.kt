package com.akinci.projectfinder.common.extension

import android.app.Activity
import android.content.Context
import android.graphics.Shader
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.akinci.projectfinder.R
import com.akinci.projectfinder.common.component.TileDrawable
import com.google.android.material.textfield.TextInputEditText

/*********************************** Tiled background related  ***********************************/
fun ImageView.setTiledImageDrawable(tiledPattern: Int) {
    val backgroundDrawable = ContextCompat.getDrawable(context, tiledPattern)
    setImageDrawable(TileDrawable(backgroundDrawable!!, Shader.TileMode.REPEAT))
}
/*************************************************************************************************/

/***************************************** Animation *********************************************/
fun View.animateAlpha(value: Float, duration: Long){
    animate().alpha(value)
             .setDuration(duration)
             .start()
}
/*************************************************************************************************/

/********************************* Keyboard management related  *********************************/
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.showKeyboardForView(view: View) {
    view.requestFocus()
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
/*************************************************************************************************/

/**************************** TextInputEditText validation extensions ****************************/
fun TextInputEditText.validateNotEmpty() : Boolean {
    error = if(text.isNullOrEmpty()){
        resources.getString(R.string.validation_text_input_edit_text_not_empty)
    }else{ null }

    return TextUtils.isEmpty(error) && !TextUtils.isEmpty(text)
}
fun TextInputEditText.validateNotBlank() : Boolean {
    error = if(text.isNullOrBlank()){
        resources.getString(R.string.validation_text_input_edit_text_not_blank)
    }else{ null }

    return TextUtils.isEmpty(error) && !TextUtils.isEmpty(text)
}
/************************************************************************************************/