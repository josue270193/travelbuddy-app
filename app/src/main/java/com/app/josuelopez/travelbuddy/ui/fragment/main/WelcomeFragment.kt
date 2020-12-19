package com.app.josuelopez.travelbuddy.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.app.josuelopez.travelbuddy.R
import com.app.josuelopez.travelbuddy.databinding.FragmentMainWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentMainWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_welcome, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonGoLoginWelcome.setOnClickListener {
            val direction = WelcomeFragmentDirections.actionMainWelcomeToMainNewUser()
            it.findNavController().navigate(direction)
        }
        binding.textGoNewUserWelcome.setOnClickListener {
            val direction = WelcomeFragmentDirections.actionMainWelcomeToMainLogin()
            it.findNavController().navigate(direction)
        }
    }
}