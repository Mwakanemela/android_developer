package com.example.scalercompass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var sensor : Sensor? = null
    private var sensorManager: SensorManager? = null
    private lateinit var compassImage: ImageView
    private lateinit var rotationTv: TextView

    private var currentDegree = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        compassImage = findViewById(R.id.imageView)
        rotationTv = findViewById(R.id.textView)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        var degree = event!!.values[0].roundToInt()
        rotationTv.text = degree.toString() + " degrees"
        Log.d("TAG", "onSensorChanged: $degree ")
        var rotationAnimation = RotateAnimation(currentDegree, (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotationAnimation.duration = 210
        rotationAnimation.fillAfter = true
        compassImage.startAnimation(rotationAnimation)
        currentDegree = (-degree).toFloat()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    //register sensor listener
    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    //unregister listener
    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}