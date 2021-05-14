package com.akinci.projectfinder

import com.akinci.projectfinder.common.activity.RootActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : RootActivity() {
    override fun getNavigationGraph(): Int = R.navigation.navigation_root
    override fun getFragmentsThatHidesBackButton(): Set<Int> = setOf(R.id.splashFragment, R.id.repoDashboardFragment)
}