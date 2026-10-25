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
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val listViewModel: ListViewModel by viewModels()

    private var binding: FragmentListBinding? = null
    private var demoObjectAdapter: DemoObjectAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentListBinding.inflate(LayoutInflater.from(context))
        binding?.startButton?.setOnClickListener {
            listViewModel.getDemoObjectFromApi()
        }
        setDemoObjectAdapter()
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
            demoObjectsFromApiLiveData.observe(viewLifecycleOwner) { demoObjects ->
                listViewModel.insertDemoObjectsToDB(demoObjects)
            }
            demoObjectsToDBInsertedLiveData.observe(viewLifecycleOwner) {
                listViewModel.getDemoObjectFromDB()
            }
            demoObjectsFromDBLiveData.observe(viewLifecycleOwner) { demoObjectUIS ->
                demoObjectAdapter?.setDemoObjects(demoObjectUIS)
            }
        }
    }

    private fun setDemoObjectAdapter() {
        demoObjectAdapter = demoObjectAdapter ?: DemoObjectAdapter(listOf())
        binding?.recyclerView?.adapter = demoObjectAdapter
        demoObjectAdapter?.setOnDemoObjectClickListener(object : OnDemoObjectClickListener {
            override fun onDemoObjectClick(demoObjectUI: DemoObjectUI) {
                findNavController().navigate(
                    ListFragmentDirections.startDetailsFragment(
                        demoObjectUI.id ?: 0
                    )
                )
            }
        })
    }
}