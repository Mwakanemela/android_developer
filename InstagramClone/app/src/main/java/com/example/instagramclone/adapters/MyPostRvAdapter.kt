package com.example.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Models.Post
import com.example.instagramclone.databinding.MyPostDesignBinding
import com.squareup.picasso.Picasso

class MyPostRvAdapter(var context:Context, var postList:ArrayList<Post>): RecyclerView.Adapter<MyPostRvAdapter.ViewHolder>() {
    inner class ViewHolder(var binding:MyPostDesignBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = MyPostDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postList[position]

        // Access the postUrl property using data binding and load the image with Picasso
        val imageUrl = post.postUrl
        Picasso.get().load(imageUrl).into(holder.binding.postImage)
    }

}