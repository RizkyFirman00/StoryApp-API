package com.example.submissionintermediate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionintermediate.DataClass.StoryModel
import com.example.submissionintermediate.databinding.ItemRvBinding

class StoriesAdapter(
    private val data: MutableList<StoryModel> = mutableListOf(),
    private val clickListener: (StoryModel) -> Unit
) : RecyclerView.Adapter<StoriesAdapter.StoriesViewHolder>() {

    fun setData(data: List<StoryModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class StoriesViewHolder(private val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoryModel) {
            binding.apply {
                tvUsername.text = item.name
                Glide.with(itemView)
                    .load(item.photo_url)
                    .into(binding.ivPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val itemStoryBinding =
            ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoriesViewHolder(itemStoryBinding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }
}