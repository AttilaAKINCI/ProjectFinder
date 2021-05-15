package com.akinci.projectfinder.features.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.akinci.projectfinder.R
import com.akinci.projectfinder.common.component.SnackBar
import com.akinci.projectfinder.common.component.listview.ShimmerAdapter
import com.akinci.projectfinder.common.extension.*
import com.akinci.projectfinder.common.helper.Resource
import com.akinci.projectfinder.databinding.FragmentRepoDashboardBinding
import com.akinci.projectfinder.features.dashboard.adapter.RepoListAdapter
import com.akinci.projectfinder.features.dashboard.view.RepoDashboardFragmentDirections.Companion.actionRepoDashboardFragmentToRepoDetailFragment
import com.akinci.projectfinder.features.dashboard.viewmodel.RepoDashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RepoDashboardFragment : Fragment() {

    lateinit var binding: FragmentRepoDashboardBinding
    private val repoDashboardViewModel : RepoDashboardViewModel by activityViewModels()

    private val shimmerAdapter = ShimmerAdapter()
    private lateinit var repoListAdapter : RepoListAdapter

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
        binding.tileBackground.setTiledImageDrawable(R.drawable.pattern)

        // repo listing adapter
        repoListAdapter = RepoListAdapter(clickListener = { repoRowId ->
            // catch news row clicks and navigate to repo Detail fragment
            Timber.d("Navigation to repo detail fragment")

            NavHostFragment.findNavController(this).navigate(
                actionRepoDashboardFragmentToRepoDetailFragment(repoRowId)
            )
        })

        // search button click action
        binding.buttonSearch.setOnClickListener {
            // valide search input.
            if(binding.editTextSearchText.validateNotEmpty() &&
                    binding.editTextSearchText.validateNotBlank()){
                // not null, not empty and not blank key can be searched.
                // fields are valid. Initiate search
                binding.editTextSearchText.clearFocus()
                hideKeyboard()
                repoDashboardViewModel.fetchRepositories(binding.editTextSearchText.text.toString())
            }else{
                SnackBar.make(binding.root,  "Please check input fields.", SnackBar.LENGTH_LONG).show()
            }
        }

        Timber.d("RepoDashboardFragment created..")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repoDashboardViewModel.searchFocusEnabled.observe(viewLifecycleOwner){
            if(it){
                // automatically call focus for search. (because we don't have any data in initial load)
                requireActivity().showKeyboardForView(binding.editTextSearchText)
                repoDashboardViewModel.searchFocusEnabled.value = false
            }
        }

        repoDashboardViewModel.listData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    Timber.d("Shimmer activated")
                    binding.recyclerList.adapter = shimmerAdapter
                }
                is Resource.Success -> {
                    // send data to news adapter
                    it.data?.let { data ->
                        Timber.d("repos are displayed")
                        binding.recyclerList.adapter = repoListAdapter
                        repoListAdapter.submitList(data)
                    }
                }
                is Resource.Error -> {
                    // show error message on snackBar
                    SnackBar.makeLarge(binding.root, it.message, SnackBar.LENGTH_LONG).show()
                }

            }
        }
    }

}