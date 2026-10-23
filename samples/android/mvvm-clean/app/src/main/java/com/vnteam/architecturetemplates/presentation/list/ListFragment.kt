package com.vnteam.architecturetemplates.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vnteam.architecturetemplates.databinding.FragmentListBinding
import com.vnteam.architecturetemplates.presentation.uimodels.ForkUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val listViewModel: ListViewModel by viewModels()

    private var binding: FragmentListBinding? = null
    private var forkAdapter: ForkAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentListBinding.inflate(LayoutInflater.from(context))
        binding?.startButton?.setOnClickListener {
            listViewModel.getForksFromApi()
        }
        setForkAdapter()
        observeLiveData()
        return binding?.root
    }

    private fun observeLiveData() {
        with(listViewModel) {
            progressVisibilityLiveData.observe(viewLifecycleOwner) { showProgress ->
                binding?.progressBar?.isVisible = showProgress
            }
            errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
            forksFromApiLiveData.observe(viewLifecycleOwner) { forks ->
                listViewModel.insertForksToDB(forks)
            }
            forksToDBInsertedLiveData.observe(viewLifecycleOwner) {
                listViewModel.getForksFromDB()
            }
            forksFromDBLiveData.observe(viewLifecycleOwner) { forks ->
                forkAdapter?.setForks(forks)
            }
        }
    }

    private fun setForkAdapter() {
        forkAdapter = forkAdapter ?: ForkAdapter(listOf())
        binding?.recyclerView?.adapter = forkAdapter
        forkAdapter?.setOnForkClickListener(object : OnForkClickListener {
            override fun onForkClick(fork: ForkUI) {
                findNavController().navigate(
                    ListFragmentDirections.startDetailsFragment(
                        fork.id ?: 0
                    )
                )
            }
        })
    }
}