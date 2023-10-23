package com.example.fragmentdemo2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText


class FirstFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_first, container, false)

        val btnFr1: Button = view.findViewById(R.id.btnFr1)
        val etFr1:EditText = view.findViewById(R.id.edtFr1)

        val secondFragment = SecondFragment()

        btnFr1.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("text", etFr1.text.toString())
            secondFragment.arguments = bundle

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, secondFragment)
                addToBackStack(null)
                commit()

            }
        }

        return view
    }


}