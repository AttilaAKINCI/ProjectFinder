package com.akinci.projectfinder.features.detail.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.akinci.projectfinder.R
import com.akinci.projectfinder.common.component.SnackBar
import com.akinci.projectfinder.common.component.SnackBar.Companion.LENGTH_SHORT
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

        setHasOptionsMenu(true)

        // set tile background
        binding.tileBackground.setTiledImageDrawable(R.drawable.pattern)

        Timber.d("RepoDetailFragment created..")
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // get selected repo from Dashboard VM and insert Detail VM
      //  repoDetailViewModel.insertRepoResponse(repoDashboardViewModel.getSelectedRepository(repoDetailFragmentArgs.repoId))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add_remove_fav) {
            /** initiate fav action here **/

//            repoDetailViewModel.setToFavorites()
//            repoDetailViewModel.removeFromFavorites()
            // send db request here..
            SnackBar.make(binding.root, "option item selected.", LENGTH_SHORT)

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

          // according to db request response update fav icon here.

    }

}