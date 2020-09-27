package com.akinci.repolisting.features.repositoryViewer.repoListing.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView
import com.akinci.repolisting.R
import com.akinci.repolisting.commons.components.fragment.BaseFragment
import com.akinci.repolisting.commons.components.list.RecyclerViewClickListener
import com.akinci.repolisting.commons.data.coroutines.Status
import com.akinci.repolisting.commons.data.model.ListRowDataContract
import com.akinci.repolisting.commons.utils.hideKeyboard
import com.akinci.repolisting.databinding.FragmentRepoListBinding
import com.akinci.repolisting.features.repositoryViewer.repoListing.components.RepoListAdapter
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.DBConstants.Companion.REPO_NAME
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.model.RepoRowModel
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.model.ShimmerRowModel
import com.akinci.repolisting.features.repositoryViewer.repoListing.viewmodel.RepoListViewModel

class RepoListFragment : BaseFragment() {

    private lateinit var binding : FragmentRepoListBinding
    private val viewModel : RepoListViewModel by navGraphViewModels(R.id.navigation_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repo_list, container, false)

        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, RepoListViewModel.Factory(activity.application)).get(RepoListViewModel::class.java)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = RepoListAdapter(RecyclerViewClickListener { position, data ->
            /** click actions for position and dataId **/
            if (data is RepoRowModel) {
                Toast.makeText(
                    activity,
                    "${data.name} repository detail is listing",
                    Toast.LENGTH_LONG
                ).show()

                NavHostFragment.findNavController(this).navigate(RepoListFragmentDirections.actionRepoListFragmentToRepoDetailFragment(position))
            }
        })

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver (){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if(positionStart == 0) {
                    binding.recyclerRepoList.layoutManager!!.scrollToPosition(0)
                }
            }
        })

        // Check repoListing Args for FAV star update.
        var repoListingArgs = RepoListFragmentArgs.fromBundle(requireArguments())
        viewModel.updateFavStatus(repoListingArgs.repoId, repoListingArgs.isFav)

        viewModel.listContent?.let {
            //  we already have data to show.
            if(it.size > 0){
                adapter.submitList(it)
            }
        }

        binding.searchButton.setOnClickListener {
            viewModel.ownerName = binding.searchEditText.text.toString()
            binding.searchEditText.clearFocus()
            it.hideKeyboard()

            val searchText = binding.searchEditText.text.toString()
            if(searchText.isNotBlank()){
                viewModel.getRemoteData(searchText).observe(viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data?.let { repos -> adapter.submitList(repos) }
                            }
                            Status.ERROR -> {
                                Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                            }
                            Status.LOADING -> {
                                val dummyShimmerList = mutableListOf<ShimmerRowModel>()
                                val rand = (1 until 10).random()
                                for(i in 0..rand){ dummyShimmerList.add(ShimmerRowModel()) }
                                adapter.submitList(dummyShimmerList as List<ListRowDataContract>?)
                            }
                        }
                    }
                })
            }
        }

        binding.recyclerRepoList.adapter = adapter
        return binding.root
    }
}