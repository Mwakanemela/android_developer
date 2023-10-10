package com.example.servicedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.servicedemo.databinding.ActivityMainBinding
import android.util.Log
import com.example.servicedemo.MyBackgroundService.Companion.MYTAG
import com.example.servicedemo.MyBackgroundService.Companion.NAME

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceIntent = Intent(this, MyBackgroundService::class.java)
        serviceIntent.putExtra(NAME, "Mwaka")
        binding.btnStart.setOnClickListener {

            Log.i(MYTAG, "Starting Service")
            startService(serviceIntent)
        }
        binding.btnStop.setOnClickListener {
            Log.i(MYTAG, "Starting Service")
            stopService(serviceIntent)
        }
    }
}