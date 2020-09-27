package com.akinci.repolisting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.akinci.repolisting.commons.components.fragment.BaseFragment
import com.akinci.repolisting.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private lateinit var navigationController: NavController
    private lateinit var binding : ActivityRootBinding

    lateinit var fragment : BaseFragment
    var toolbarIconSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_root)

        // setup navigation
        navigationController = findNavController(R.id.nav_host_fragment)
        // tell navigation controller that which fragments will be at the top of backstack
        // (hides backbutton for fragments which are placed at top)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.splashScreenFragment,
                R.id.repoListFragment
            )
        )

        // remove extra padding between arrow and toolbar title
        binding.toolbar.contentInsetStartWithNavigation = 10
        binding.toolbar.clickListener = View.OnClickListener {
            toggleStarButton()
            fragment?.let {
                fragment.rightBarIconClicked()
            }
        }

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navigationController, appBarConfiguration)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun resetRightBarIcons(){ binding.toolbar.resetBarItems() }
    private fun toggleStarButton(){ addRightStarButton(!toolbarIconSelected) }
    fun addRightStarButton(isSelected: Boolean){
        toolbarIconSelected = isSelected
        val startColor = ContextCompat.getColor(this, R.color.rowStarColor)
        val selectedIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_star, theme)
        val unSelectedIcon = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_star_border,
            theme
        )
        if(isSelected){
            selectedIcon?.let {
                it.setTint(startColor)
                binding.toolbar.setRightBarIcon(it) }
        }else{
            unSelectedIcon?.let {
                it.setTint(startColor)
                binding.toolbar.setRightBarIcon(it) }
        }
    }
}