package com.example.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.Models.User
import com.example.instagramclone.databinding.ActivitySignUpBinding
import com.example.instagramclone.utils.USER_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding
    private lateinit var user:User


    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        uri ->
        uri?.let {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = User()

        binding.register.setOnClickListener {
           val name = binding.name.text.toString().trim()
           val email = binding.email.text.toString().trim()
           val password = binding.password.text.toString().trim()

            //val db = Firebase.firestore
            if((binding.name.text.toString().trim() == "")
                or (binding.name.text.toString().trim() == "")
                or (binding.name.text.toString().trim() == "")
            ) {
                Toast.makeText(this@SignUpActivity, "Fill out all fields", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@SignUpActivity, "Lembani muma box onse", Toast.LENGTH_SHORT).show()

            }else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email, password
                ).addOnCompleteListener {
                    result ->
                    if(result.isSuccessful) {

                        user.name = name
                        user.email = email
                        user.password = password
                        Firebase.firestore.collection(USER_NODE)
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this@SignUpActivity, "Kulembetsa Kwatheka", Toast.LENGTH_SHORT).show()

                            }
                        Toast.makeText(this@SignUpActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this@SignUpActivity, "Kulembetsa Kwatheka", Toast.LENGTH_SHORT).show()

                        binding.name.setText("")
                        binding.email.setText("")
                        binding.password.setText("")
                    }
                }
            }
        }
    }
}