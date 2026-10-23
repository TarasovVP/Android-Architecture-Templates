package com.vnteam.architecturetemplates.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vnteam.architecturetemplates.databinding.ItemDemoObjectBinding
import com.vnteam.architecturetemplates.models.DemoObject

class DemoObjectAdapter(private var demoObjects: List<DemoObject>) :
    RecyclerView.Adapter<DemoObjectAdapter.ViewHolder>() {

    private var onDemoObjectClickListener: OnDemoObjectClickListener? = null

    class ViewHolder(private val binding: ItemDemoObjectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(demoObject: DemoObject, onDemoObjectClickListener: OnDemoObjectClickListener?) {
            binding.demoObjectName.text = demoObject.name
            binding.root.setOnClickListener {
                onDemoObjectClickListener?.onDemoObjectClick(demoObject)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDemoObjectBinding.inflate( LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val demoObject = demoObjects[position]
        holder.bind(demoObject, onDemoObjectClickListener)
    }

    override fun getItemCount(): Int {
        return demoObjects.size
    }

    fun setOnDemoObjectClickListener(onDemoObjectClickListener: OnDemoObjectClickListener) {
        this.onDemoObjectClickListener = onDemoObjectClickListener
    }

    fun setDemoObjects(demoObjects: List<DemoObject>) {
        this.demoObjects = demoObjects
        notifyDataSetChanged()
    }
}