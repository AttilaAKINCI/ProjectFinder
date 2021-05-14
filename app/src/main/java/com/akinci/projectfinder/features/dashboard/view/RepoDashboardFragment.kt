package com.akinci.projectfinder.features.dashboard.view

import android.graphics.Shader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.akinci.projectfinder.R
import com.akinci.projectfinder.common.component.TileDrawable
import com.akinci.projectfinder.databinding.FragmentRepoDashboardBinding
import com.akinci.projectfinder.features.dashboard.viewmodel.RepoDashboardViewModel
import timber.log.Timber

class RepoDashboardFragment : Fragment() {

    lateinit var binding: FragmentRepoDashboardBinding
    private val repoDashboardViewModel : RepoDashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // Inflate the layout for this fragment
        /** Initialization of ViewBinding not need for DataBinding here **/
        binding = FragmentRepoDashboardBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        // show appbar on repo dashboard
        (activity as AppCompatActivity).supportActionBar?.show()

        // set tile background
        val backgroundDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.pattern)
        binding.tileBackground.setImageDrawable(TileDrawable(backgroundDrawable!!, Shader.TileMode.REPEAT))

        Timber.d("RepoDashboardFragment created..")
        return binding.root
    }

}