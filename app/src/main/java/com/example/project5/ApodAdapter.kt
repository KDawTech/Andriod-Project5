package com.example.project5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project5.databinding.ItemApodBinding

class ApodAdapter(private var items: List<ApodResponse>) :
    RecyclerView.Adapter<ApodAdapter.ApodVH>() {

    inner class ApodVH(val binding: ItemApodBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodVH {
        val binding = ItemApodBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ApodVH(binding)
    }

    override fun onBindViewHolder(holder: ApodVH, position: Int) {
        val apod = items[position]
        holder.binding.apply {
            titleText.text = apod.title
            descText.text = apod.explanation
            Glide.with(imageView.context).load(apod.url).into(imageView)
        }
    }

    override fun getItemCount(): Int = items.size

    fun update(newList: List<ApodResponse>) {
        items = newList
        notifyDataSetChanged()
    }
}

