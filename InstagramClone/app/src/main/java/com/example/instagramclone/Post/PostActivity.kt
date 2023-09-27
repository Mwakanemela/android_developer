package com.example.instagramclone.Post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.HomeActivity
import com.example.instagramclone.Models.Post
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ActivityPostBinding
import com.example.instagramclone.utils.POST
import com.example.instagramclone.utils.POST_FOLDER
import com.example.instagramclone.utils.USER_PROFILE_FOLDER
import com.example.instagramclone.utils.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl:String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
//            uploadImage(uri, POST_FOLDER) {uri ->
//                if (uri == null) {
//                    binding.selectImage.setImageURI(uri)
//                    //imageUrl
//                    Log.d("Post Activity", "image uri - $uri")
//                    imageUrl= uri.toString()
//                }
//            }

            uploadImage(uri, USER_PROFILE_FOLDER) {
                if (it == null) {
                    Toast.makeText(this@PostActivity, "Cannot accept image", Toast.LENGTH_LONG)
                        .show()
                } else {
                    binding.selectImage.setImageURI(uri)
                    imageUrl = it

                }
            }


        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //when u click the back button goto prev screen
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }
        binding.postButton.setOnClickListener {
            val post = Post(imageUrl!!, binding.postTxt.text.toString())

            Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                //create new document with new id
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
                    .document().set(post)
                    .addOnSuccessListener {
                        startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                        finish()
                    }

            }
        }
    }
}