package com.example.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.instagramclone.Models.Post
import com.example.instagramclone.Models.Reel
import com.example.instagramclone.databinding.MyPostDesignBinding
import com.squareup.picasso.Picasso


class MyReelRvAdapter(var context: Context, var reelList:ArrayList<Reel>): RecyclerView.Adapter<MyReelRvAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: MyPostDesignBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = MyPostDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reel = reelList[position]

        // Access the postUrl property using data binding and load the image with Picasso
        val reelUrl = reel.reelUrl
        Glide.with(context)
            .load(reel.reelUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.postImage)
        //Picasso.get().load(imageUrl).into(holder.binding.postImage)
    }

}