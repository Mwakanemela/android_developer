package com.example.fragmentdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()

        val mainActivityTv: TextView = findViewById(R.id.inActivityTv)
        val btn1: Button = findViewById(R.id.firstFragmentButton)
        val btn2: Button = findViewById(R.id.secondFragmentButton)

        btn1.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, firstFragment)
                addToBackStack(null)
                commit()

                mainActivityTv.visibility = INVISIBLE
            }
        }
        btn2.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, secondFragment)
                addToBackStack(null)
                commit()

                mainActivityTv.visibility = INVISIBLE
            }
        }
    }
}