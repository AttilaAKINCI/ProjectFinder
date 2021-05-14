package com.akinci.projectfinder.features.splash.view

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.akinci.projectfinder.R
import com.akinci.projectfinder.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        /** Initialization of ViewBinding not need for DataBinding here **/
        binding = FragmentSplashBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner

        //hide appbar on splash screen
        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.animation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) { navigate() }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })

        Timber.d("SplashFragment created..")
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // when observe anything, start animation.
        binding.animation.playAnimation()
    }

    private fun navigate(){
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToRepoDashboard()
        }, 100)
    }

    private fun navigateToRepoDashboard(){
        /** Navigate to Repo Dashboard Page **/
        NavHostFragment.findNavController(this).navigate(R.id.action_splashFragment_to_repoDashboardFragment)
    }

}