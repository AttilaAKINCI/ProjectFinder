package com.akinci.repolisting.features.repositoryViewer.repoDetail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.akinci.repolisting.R
import com.akinci.repolisting.RootActivity
import com.akinci.repolisting.commons.components.fragment.BaseFragment
import com.akinci.repolisting.databinding.FragmentRepoDetailBinding
import com.akinci.repolisting.features.repositoryViewer.repoDetail.viewmodel.RepoDetailViewModel
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.DBConstants
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.RepoDatabase
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.model.RepoRowModel
import com.akinci.repolisting.features.repositoryViewer.repoListing.viewmodel.RepoListViewModel
import com.bumptech.glide.Glide

class RepoDetailFragment : BaseFragment() {

    private lateinit var binding : FragmentRepoDetailBinding

    private val viewModel : RepoDetailViewModel by lazy {
        val activity = requireNotNull(this.activity) { "viewModel will be created after onActivityCreated() lifecycle function" }
        val repoDetailArgs = RepoDetailFragmentArgs.fromBundle(requireArguments())
        val dataSource = RepoDatabase.getInstance(activity.application).repoDAO

        // Fetch ListViewModel which is scoped on navigation graph
        val viewModelProvider = ViewModelProvider(
            findNavController().getViewModelStoreOwner(R.id.navigation_graph),
            ViewModelProvider.AndroidViewModelFactory(activity.application)
        )
        val repoListVM = viewModelProvider.get(RepoListViewModel::class.java)
        val detailData = repoListVM.listContent[repoDetailArgs.repoListIndex] as RepoRowModel

        /** pass context for DB related initializations. **/
        ViewModelProvider(this, RepoDetailViewModel.Factory(activity.application, dataSource, detailData)).get(RepoDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repo_detail, container, false)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        Glide.with(binding.userAvatar.context)
            .load(viewModel.detailData.owner.avatar_url)
            .centerCrop()
            .into(binding.userAvatar)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { navigateToRepoList() }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        if(activity is RootActivity){ (activity as RootActivity).addRightStarButton(viewModel.detailData.isFav) }

        return binding.root
    }

    fun navigateToRepoList() {
        NavHostFragment.findNavController(this).navigate(
            RepoDetailFragmentDirections.actionRepoDetailFragmentToRepoListFragment(
                viewModel.getRepoId(),
                viewModel.getIsFav()))
    }

    override fun rightBarIconClicked(){ viewModel.toggleFav() }
}