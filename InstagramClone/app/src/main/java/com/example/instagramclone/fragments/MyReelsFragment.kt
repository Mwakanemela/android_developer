package com.example.instagramclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramclone.Models.Post
import com.example.instagramclone.Models.Reel
import com.example.instagramclone.R
import com.example.instagramclone.adapters.MyPostRvAdapter
import com.example.instagramclone.adapters.MyReelRvAdapter
import com.example.instagramclone.databinding.ActivityReelsBinding
import com.example.instagramclone.databinding.FragmentMyPostBinding
import com.example.instagramclone.databinding.FragmentMyReelsBinding
import com.example.instagramclone.utils.REEL
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MyReelsFragment : Fragment() {


    private lateinit var binding: FragmentMyReelsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyReelsBinding.inflate(inflater, container, false)

        var reelList = ArrayList<Reel>()
        var adapter = MyReelRvAdapter(requireContext(), reelList)
        binding.reelRecyclerView.layoutManager = StaggeredGridLayoutManager(
            3, StaggeredGridLayoutManager.VERTICAL
        )
        binding.reelRecyclerView.adapter = adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+REEL).get().addOnSuccessListener {
            var temporaryList = arrayListOf<Reel>()
            for(i in it.documents) {
                var reel: Reel = i.toObject<Reel>()!!
                temporaryList.add(reel)
            }

            reelList.addAll(temporaryList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }
}