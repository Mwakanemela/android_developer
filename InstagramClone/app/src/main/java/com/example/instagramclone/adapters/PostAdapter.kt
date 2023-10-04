package com.example.instagramclone.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.Models.Post
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.example.instagramclone.databinding.PostRvBinding
import com.example.instagramclone.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class PostAdapter (var context: Context, var postList:ArrayList<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>(){

    inner class ViewHolder(var binding: PostRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = PostRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            Firebase.firestore.collection(USER_NODE)
                .document(postList[position].uid).get().addOnSuccessListener {
                    var user = it.toObject<User>()
                    Glide.with(context).load(user!!.image).placeholder(R.drawable.profile).into(holder.binding.profileImage)
                    holder.binding.postName.text = user.name
                }
        }catch (e: Exception) {

        }

        Glide.with(context).load(postList[position].postUrl)
            .placeholder(R.drawable.profile)
            .into(holder.binding.postFile)

        try {

            val text: String = TimeAgo.using(postList[position].time.toLong())

            holder.binding.postDesc.text = text
        }catch (e:java.lang.Exception) {
            holder.binding.postDesc.text = ""
        }
        holder.binding.postCaption.text = postList[position].caption
        holder.binding.like.setOnClickListener {
            Toast.makeText(context, "Liked post", Toast.LENGTH_LONG).show()
        }
        holder.binding.postShare.setOnClickListener {
            var intent = Intent(Intent.ACTION_SEND)
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, postList[position].postUrl)
            context.startActivity(intent)
        }
    }
}