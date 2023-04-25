package com.example.submissionintermediate.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class StoriesBaseAdapter<T, V : ViewBinding>(
    private val diffUtilCallbackListener: DiffCallbackListener<T>
) : RecyclerView.Adapter<StoriesBaseAdapter.BaseViewHolder>() {

    var data = mutableListOf<T>()
    abstract fun onCreateViewHolder(inflater: LayoutInflater, container: ViewGroup): ViewBinding
    abstract fun bind(binding: V, item: T, position: Int, count: Int)

    fun dataSet(items: List<T>) {
        val diffResult = DiffUtil.calculateDiff(
            diffUtilCallbackListener(
                data,
                items.toMutableList(),
                diffUtilCallbackListener
            )
        )
        data = items.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    class BaseViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        return BaseViewHolder(onCreateViewHolder(LayoutInflater.from(parent.context), parent))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =
        bind(holder.binding as V, data[position], position, data.size)

    override fun getItemCount(): Int {
        return data.size
    }
}

class diffUtilCallbackListener<T>(
    private val new: List<T>,
    private val old: List<T>,
    private val listener: DiffCallbackListener<T>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listener.areItemsTheSame(old[oldItemPosition], new[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }
}

interface DiffCallbackListener<T> {
    fun areItemsTheSame(oldItem: T, newItem: T): Boolean
}