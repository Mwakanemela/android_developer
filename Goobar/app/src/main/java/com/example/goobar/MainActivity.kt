package com.example.goobar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.goobar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val forecastRepository = ForecastRepository()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            val zipCode:String = binding.zipCodeEditText.text.toString().trim()

            if(zipCode.length != 5) {
                showToast(zipCode)
            }else {
                forecastRepository.loadForecast(zipCode)
            }
        }

        val weeklyForecastObserver = Observer<List<DailyForecast>> {
            forecastItems ->
            //update list adapter
            showToast("Load Items")
        }

        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)
    }

    private fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}