package com.example.servicedemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyBackgroundService:Service(){

    init {
        Log.i(MYTAG, "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.i(MYTAG, "Service Started")
        return START_STICKY
    }
    override fun onBind(p0: Intent?): IBinder? {
       return null
    }

    override fun onDestroy() {
        Log.i(MYTAG, "Destroy service")
        super.onDestroy()
    }

    companion object{

        const val MYTAG = "MYTAG"
    }
}