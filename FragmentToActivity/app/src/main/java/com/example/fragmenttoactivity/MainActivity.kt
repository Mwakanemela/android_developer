package com.example.fragmenttoactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), MyInterface {

    private lateinit var text1:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = FirstFragment()

        text1 = findViewById(R.id.txt1)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, firstFragment)
                .addToBackStack("firstFragment")
                .commit()
        }

//        val intent = Intent()
//
//        val stringText = intent.getStringExtra("string")
//        text1.text = stringText
//        Toast.makeText(this, stringText, Toast.LENGTH_SHORT).show()
    }

    override fun transferredMessage(msg: String) {
        text1.text = msg
    }
}