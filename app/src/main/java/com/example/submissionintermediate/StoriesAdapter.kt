package com.example.submissionintermediate

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import androidx.core.util.Pair
import com.example.submissionintermediate.DataClass.Model.StoryModelEntity
import com.example.submissionintermediate.databinding.ItemRvBinding
import com.example.submissionintermediate.utils.StoriesBaseAdapter
import com.example.submissionintermediate.utils.diffUtilCallbackListener

class StoriesAdapter :
    StoriesBaseAdapter<StoryModelEntity, ItemRvBinding>(diffUtilCallbackListener = diffUtilStory) {

    override fun onCreateViewHolder(inflater: LayoutInflater, container: ViewGroup) =
        ItemRvBinding.inflate(inflater, container, false)

    override fun bind(binding: ItemRvBinding, item: StoryModelEntity, position: Int, count: Int) {
        binding.apply {
            tvUsername.text = item.name
            tvDescription.text = item.description
            Glide.with(binding.root.context)
                .load(item.photoUrl)
                .into(binding.ivPhoto)
        }
        binding.root.setOnClickListener {
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    binding.root.context as MainActivity,
                    Pair(binding.ivPhoto, "photo"),
                    Pair(binding.tvUsername, "name"),
                    Pair(binding.tvDescription, "description")
                )

            binding.root.context.startActivity(
                Intent(
                    binding.root.context, DetailActivity::class.java
                ).putExtra(DetailActivity.STORY_ID, item.id), optionsCompat.toBundle(
                )
            )
        }
    }

    override fun getItemCount(): Int = data.size

    companion object {
        val diffUtilStory = object : diffUtilCallbackListener.DiffCallbackListener<StoryModelEntity> {
                override fun areItemsTheSame(
                    oldItem: StoryModelEntity,
                    newItem: StoryModelEntity
                ) = oldItem.id == newItem.id
            }
    }
}