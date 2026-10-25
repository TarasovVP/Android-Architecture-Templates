package com.vnteam.architecturetemplates.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.vnteam.architecturetemplates.R
import com.vnteam.architecturetemplates.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsBinding.inflate(LayoutInflater.from(context))
        observeLiveData()
        binding?.backButton?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        detailsViewModel.getDemoObjectById(args.demoObjectId)
        return binding?.root
    }

    private fun observeLiveData() {
        with(detailsViewModel) {
            progressVisibilityLiveData.observe(viewLifecycleOwner) { showProgress ->
                binding?.progressBar?.isVisible = showProgress
            }
            errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
            demoObjectLiveData.observe(viewLifecycleOwner) { demoObject ->
                binding?.apply {
                    demoObjectName.text = demoObject.name
                    ownerName.text = demoObject.owner?.login
                    demoObjectDescription.text = demoObject.description
                    ownerAvatar.load(demoObject.owner?.avatarUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_person)
                        error(R.drawable.ic_person)
                    }
                }
            }
        }
    }
}