package com.example.imagelistpagination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagelistpagination.R
import com.example.imagelistpagination.databinding.ItemImageBinding
import com.example.imagelistpagination.model.ImageListResponse

class ImageAdapter(private val onItemClick: (ImageListResponse) -> Unit) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    private val imageList = mutableListOf<ImageListResponse>()

    fun setData(newImageList: List<ImageListResponse>) {
        imageList.addAll(newImageList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ImageViewHolder(var binding : ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(imageList[position])
                }
            }
        }

        fun bind(image: ImageListResponse) {
            binding.authorTextView.text = "By: "+image.author
            Glide.with(itemView)
                .load(image.downloadUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imageView)
        }
    }
}
