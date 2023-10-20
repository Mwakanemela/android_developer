package com.example.fragmentdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SecondFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        //val btnFr1: Button = view.findViewById(R.id.fr1Btn)
        val tvFr2: TextView = view.findViewById(R.id.tvfr2)

       val data = arguments

        tvFr2.text = "From second fragment - ${data!!.getString("String").toString()}"
        return view
    }
}