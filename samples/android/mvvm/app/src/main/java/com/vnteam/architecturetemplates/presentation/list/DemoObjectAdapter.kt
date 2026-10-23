package com.vnteam.architecturetemplates.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vnteam.architecturetemplates.databinding.ItemDemoObjectBinding
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI

class DemoObjectAdapter(private var demoObjects: List<DemoObjectUI>) :
    RecyclerView.Adapter<DemoObjectAdapter.ViewHolder>() {

    private var onDemoObjectClickListener: OnDemoObjectClickListener? = null

    class ViewHolder(private val binding: ItemDemoObjectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(demoObjectUI: DemoObjectUI, onDemoObjectClickListener: OnDemoObjectClickListener?) {
            binding.demoObjectName.text = demoObjectUI.name
            binding.root.setOnClickListener {
                onDemoObjectClickListener?.onDemoObjectClick(demoObjectUI)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDemoObjectBinding.inflate( LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val demoObjectUI = demoObjects[position]
        holder.bind(demoObjectUI, onDemoObjectClickListener)
    }

    override fun getItemCount(): Int {
        return demoObjects.size
    }

    fun setOnDemoObjectClickListener(onDemoObjectClickListener: OnDemoObjectClickListener) {
        this.onDemoObjectClickListener = onDemoObjectClickListener
    }

    fun setDemoObjects(demoObjectUIS: List<DemoObjectUI>) {
        this.demoObjects = demoObjectUIS
        notifyDataSetChanged()
    }
}