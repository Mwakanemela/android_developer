package com.example.fragmentdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class FirstFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        val btnFr1: Button = view.findViewById(R.id.fr1Btn)
        val tvFr1: TextView = view.findViewById(R.id.tvfr1)

        btnFr1.setOnClickListener {
            tvFr1.text = "Change text to get text"
        }
        return view
    }

}