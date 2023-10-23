package com.example.fragmenttoactivity

import android.content.Intent
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

        val fr1Vtb:Button = view.findViewById(R.id.btnFr1)
        val fr1Et:EditText = view.findViewById(R.id.edtFr1)

        fr1Vtb.setOnClickListener {
            val msg = fr1Et.text.toString().trim()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("string", msg)

            startActivity(intent)
        }
        return view
    }

}