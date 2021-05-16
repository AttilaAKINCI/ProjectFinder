package com.akinci.projectfinder.features.detail.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionListenerAdapter
import com.akinci.projectfinder.R
import com.akinci.projectfinder.common.activity.RootActivity
import com.akinci.projectfinder.common.component.SnackBar
import com.akinci.projectfinder.common.component.SnackBar.Companion.LENGTH_SHORT
import com.akinci.projectfinder.common.extension.animateAlpha
import com.akinci.projectfinder.common.extension.setGlideImageCentered
import com.akinci.projectfinder.common.extension.setTiledImageDrawable
import com.akinci.projectfinder.databinding.FragmentRepoDetailBinding
import com.akinci.projectfinder.features.dashboard.viewmodel.RepoDashboardViewModel
import com.akinci.projectfinder.features.detail.viewmodel.RepoDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RepoDetailFragment : Fragment() {

    lateinit var binding: FragmentRepoDetailBinding

    private val repoDashboardViewModel : RepoDashboardViewModel by activityViewModels()
    private val repoDetailViewModel : RepoDetailViewModel by viewModels()

    private val repoDetailFragmentArgs by lazy { RepoDetailFragmentArgs.fromBundle(requireArguments()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // Inflate the layout for this fragment
        /** Initialization of ViewBinding not need for DataBinding here **/
        binding = FragmentRepoDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = repoDetailViewModel

        // get selected repo from Dashboard VM and insert Detail VM
        repoDashboardViewModel.getSelectedRepository(repoDetailFragmentArgs.position)?.let {
            repoDetailViewModel.repoData = it
        }

        binding.picture.transitionName = repoDetailFragmentArgs.position.toString()
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

//        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move).addListener(object : TransitionListenerAdapter() {
//            override fun onTransitionEnd(transition: Transition) {
//                // The current fragment transition should only be applied for this transition and be removed afterwards
//                binding.ownerName.animateAlpha(1.0f, 200L)
//                binding.starCount.animateAlpha(1.0f, 200L)
//                binding.openIssueCount.animateAlpha(1.0f, 200L)
//                binding.descriptionTitle.animateAlpha(1.0f, 200L)
//                binding.description.animateAlpha(1.0f, 200L)
//                exitTransition = null
//            }
//        })

        // set detail action bar title as Repository name
        (activity as RootActivity).binding.toolbar.title = repoDetailViewModel.repoData.name
        // activate fav button on actionbar
        setHasOptionsMenu(true)

        // set tile background
        binding.tileBackground.setTiledImageDrawable(R.drawable.pattern)

        Timber.d("RepoDetailFragment created..")
        return binding.root
    }

    override fun onStart() {
        super.onStart()

    //    postponeEnterTransition()

        Handler(Looper.getMainLooper()).postDelayed({
            /** delayed content view **/
            binding.ownerName.animateAlpha(1.0f, 200L)
            binding.starCount.animateAlpha(1.0f, 200L)
            binding.openIssueCount.animateAlpha(1.0f, 200L)
            binding.descriptionTitle.animateAlpha(1.0f, 200L)
            binding.description.animateAlpha(1.0f, 200L)

            // fix card elevation here
            binding.pictureCard.cardElevation = resources.getDimension(R.dimen.cardview_default_elevation)

        }, 400)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // load detail page owner picture from URL
        binding.picture.setGlideImageCentered(
            imageUrl = repoDetailViewModel.repoData.owner.avatar_url!!,
            fallbackDrawableId = R.drawable.ic_person,
            resources.getDimensionPixelSize(R.dimen.detail_card_corner_radius))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_fav).icon = getFavStatusIconDrawable()
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_fav){
            if(repoDetailViewModel.repoData.isFavorite) {
                // pressed filled star so we need to remove from favorite list
                repoDetailViewModel.removeFromFavorites()
                repoDetailViewModel.repoData.isFavorite = false
                SnackBar.make(binding.root, "Repository is removed from favorites", LENGTH_SHORT).show()
            }else{
                // pressed empty star so we need to add to favorite list
                repoDetailViewModel.addToFavorites()
                repoDetailViewModel.repoData.isFavorite = true
                SnackBar.make(binding.root, "Repository is added to favorites", LENGTH_SHORT).show()
            }
            item.icon = getFavStatusIconDrawable()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // menu iconTint field can be used above api 26. We are using min 24 so we need to workaround it.
    private fun getFavStatusIconDrawable() = if(repoDetailViewModel.repoData.isFavorite) {
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_filled)?.apply {
            DrawableCompat.setTint(this, ContextCompat.getColor(requireContext(), R.color.yellow))
        }
    }else{
        ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_empty)?.apply {
            DrawableCompat.setTint(this, ContextCompat.getColor(requireContext(), R.color.mainBg))
        }
    }

}