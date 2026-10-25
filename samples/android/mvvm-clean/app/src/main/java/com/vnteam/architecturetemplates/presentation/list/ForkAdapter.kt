package com.vnteam.architecturetemplates.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vnteam.architecturetemplates.databinding.ItemForkBinding
import com.vnteam.architecturetemplates.presentation.uimodels.ForkUI

class ForkAdapter(private var forks: List<ForkUI>) :
    RecyclerView.Adapter<ForkAdapter.ViewHolder>() {

    private var onForkClickListener: OnForkClickListener? = null

    class ViewHolder(private val binding: ItemForkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fork: ForkUI, onForkClickListener: OnForkClickListener?) {
            binding.forkName.text = fork.name
            binding.root.setOnClickListener {
                onForkClickListener?.onForkClick(fork)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForkBinding.inflate( LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fork = forks[position]
        holder.bind(fork, onForkClickListener)
    }

    override fun getItemCount(): Int {
        return forks.size
    }

    fun setOnForkClickListener(onForkClickListener: OnForkClickListener) {
        this.onForkClickListener = onForkClickListener
    }

    fun setForks(forks: List<ForkUI>) {
        this.forks = forks
        notifyDataSetChanged()
    }
}