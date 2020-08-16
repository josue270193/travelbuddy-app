package com.app.travelbuddy.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.travelbuddy.R
import com.app.travelbuddy.databinding.FragmentMainHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentMainHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_home, container, false)
        binding.mainHomeButton.setOnClickListener(onClickButton)
        return binding.root
    }

    private val onClickButton = View.OnClickListener { _ ->
        Toast.makeText(requireActivity(), "Presionado", Toast.LENGTH_SHORT).show()
        val direction = HomeFragmentDirections.navigateToMainCity(1)
        findNavController().navigate(direction)
    }
}