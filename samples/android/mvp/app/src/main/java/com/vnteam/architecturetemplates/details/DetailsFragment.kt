package com.vnteam.architecturetemplates.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import com.vnteam.architecturetemplates.AppApplication
import com.vnteam.architecturetemplates.databinding.FragmentDetailsBinding
import com.vnteam.architecturetemplates.models.DemoObject
import javax.inject.Inject

class DetailsFragment : Fragment(), DetailsViewContract {

    @Inject
    lateinit var detailsPresenter: DetailsPresenter

    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsBinding.inflate(LayoutInflater.from(context))
        binding?.backButton?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        AppApplication.instance?.appComponent?.injectDetailsFragment(this)
        detailsPresenter.attachView(this)
        val demoObjectId =  arguments?.getLong(DEMO_OBJECT_ID)
        detailsPresenter.getDemoObjectById(demoObjectId)
        return binding?.root
    }

    override fun setProgressVisibility(showProgress: Boolean) {
        binding?.progressBar?.isVisible = showProgress
    }

    override fun setDemoObjectFromDB(demoObject: DemoObject) {
       binding?.apply {
            demoObjectName.text = demoObject.fullName
            ownerName.text = demoObject.owner?.login
            demoObjectDescription.text = demoObject.fullName
            Picasso.get().load(demoObject.owner?.avatarUrl).into(ownerAvatar)
        }
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        detailsPresenter.detachView()
        super.onDestroyView()
    }

    companion object {
        const val DEMO_OBJECT_ID = "demoObjectId"

        @JvmStatic
        fun newInstance(demoObjectId: Long?) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(DEMO_OBJECT_ID, demoObjectId)
                }
            }
    }
}