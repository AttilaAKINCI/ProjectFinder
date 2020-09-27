package com.akinci.repolisting.commons.components.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.akinci.repolisting.RootActivity
import com.akinci.repolisting.features.splash.SplashScreenFragment

abstract class BaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        when(this) {
            is SplashScreenFragment -> { (activity as AppCompatActivity).supportActionBar?.hide() }
            else -> { (activity as AppCompatActivity).supportActionBar?.show() }
        }

        if(activity is RootActivity){
            (activity as RootActivity).fragment = this
            (activity as RootActivity).resetRightBarIcons()
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    open fun rightBarIconClicked() { /** used for overrides. **/ }
}