package com.vnteam.architecturetemplates.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.vnteam.architecturetemplates.details.DetailsFragment
import com.vnteam.architecturetemplates.models.DemoObject
import com.vnteam.architecturetemplates.AppApplication
import com.vnteam.architecturetemplates.R
import com.vnteam.architecturetemplates.databinding.FragmentListBinding
import javax.inject.Inject

class ListFragment : Fragment(), ListViewContract {

    @Inject
    lateinit var listPresenter: ListPresenter

    private var binding: FragmentListBinding? = null
    private var demoObjectAdapter: DemoObjectAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentListBinding.inflate(LayoutInflater.from(context))
        AppApplication.instance?.appComponent?.injectListFragment(this)
        listPresenter.attachView(this)
        setDemoObjectAdapter()
        binding?.startButton?.setOnClickListener {
            listPresenter.getDemoObjectsFromApi()
        }
        return binding?.root
    }

    private fun setDemoObjectAdapter() {
        demoObjectAdapter = demoObjectAdapter ?: DemoObjectAdapter(listOf())
        binding?.recyclerView?.adapter = demoObjectAdapter
        demoObjectAdapter?.setOnDemoObjectClickListener(object : OnDemoObjectClickListener {
            override fun onDemoObjectClick(demoObject: DemoObject) {
                val detailsFragment = DetailsFragment.newInstance(demoObject.id)
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainer, detailsFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        })
    }

    override fun setProgressVisibility(showProgress: Boolean) {
        binding?.progressBar?.isVisible = showProgress
    }

    override fun insertDemoObjectsToDB(demoObjects: List<DemoObject>) {
        listPresenter.insertDemoObjectsToDB(demoObjects)
    }

    override fun getDemoObjectsFromDB() {
        listPresenter.getDemoObjectsFromDB()
    }

    override fun setDemoObjectsFromDB(demoObjects: List<DemoObject>) {
        demoObjectAdapter?.setDemoObjects(demoObjects)
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        listPresenter.detachView()
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}