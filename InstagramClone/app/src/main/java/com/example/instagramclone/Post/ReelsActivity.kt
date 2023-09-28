package com.example.instagramclone.Post

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.HomeActivity
import com.example.instagramclone.Models.Post
import com.example.instagramclone.Models.Reel
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.example.instagramclone.databinding.ActivityReelsBinding
import com.example.instagramclone.utils.POST
import com.example.instagramclone.utils.POST_FOLDER
import com.example.instagramclone.utils.REEL
import com.example.instagramclone.utils.REEL_FOLDER
import com.example.instagramclone.utils.USER_NODE
import com.example.instagramclone.utils.USER_PROFILE_FOLDER
import com.example.instagramclone.utils.uploadImage
import com.example.instagramclone.utils.uploadVideo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ReelsActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }
    var videoUrl:String? = null
    private lateinit var progressDialog:ProgressDialog
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

            uploadVideo(uri, REEL_FOLDER, progressDialog) {
                if (it == null) {
                    Toast.makeText(this@ReelsActivity, "Cannot accept image", Toast.LENGTH_LONG)
                        .show()
                } else {
                    //binding.selectImage.setImageURI(uri)
                    videoUrl = it

                }
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        binding.selectShorts.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {

            Firebase.firestore.collection(USER_NODE)
                .document(Firebase.auth.currentUser!!.uid)
                .get().addOnSuccessListener {
                    var user: User? = it.toObject<User>()
                    val reel = Reel(videoUrl!!, binding.reelTxt.text.toString(), user?.image!!)


                    Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                        //create new document with new id
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+REEL)
                            .document().set(reel)
                            .addOnSuccessListener {
                                startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                                finish()
                            }

                    }
                }
        }
    }
}