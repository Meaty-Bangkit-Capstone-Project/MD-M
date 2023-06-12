package com.damar.meaty.hasil

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.damar.meaty.databinding.StoryBinding
import com.damar.meaty.detail.DetailStoryActivity
import com.damar.meaty.response.Story

class ListHasilAdapter : PagingDataAdapter<Story, ListHasilAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder{
        val binding = StoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.tvItemName.text = data.name
            Glide.with(holder.itemView.context)
                .load(data.photoUrl)
                .into(holder.ivItemPhoto)
            holder.itemView.setOnClickListener{
                val intent = Intent(holder.itemView.context, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.NAMA, data.name)
                intent.putExtra(DetailStoryActivity.DESKRIPSI, data.description)
                intent.putExtra(DetailStoryActivity.GAMBAR, data.photoUrl)
                intent.putExtra(DetailStoryActivity.CREATED_AT, data.createdAt)
                holder.itemView.context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(holder.itemView.context as Activity).toBundle())
            }
        }
    }

    inner class ViewHolder(binding: StoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvItemName: TextView = binding.tvItemName
        val ivItemPhoto: ImageView = binding.ivItemPhoto
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}