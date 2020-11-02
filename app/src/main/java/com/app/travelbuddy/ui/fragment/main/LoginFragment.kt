package com.app.travelbuddy.ui.fragment.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.travelbuddy.R
import com.app.travelbuddy.databinding.FragmentMainLoginBinding
import com.app.travelbuddy.ui.viewmodel.UserViewModel
import com.app.travelbuddy.utils.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentMainLoginBinding

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        binding.buttonDoLogin.setOnClickListener {
            hideKeyboard()
            val email = binding.inputUserLogin.editText?.text.toString()
            val password = binding.inputPasswordLogin.editText?.text.toString()
            userViewModel.signIn(email, password).observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Timber.d("status: ${it.status}")
                    }
                    Resource.Status.ERROR -> {
                        Timber.d("status: ${it.status}")
                        Timber.d("message: ${it.message}")
                        Snackbar.make(
                            binding.constraintLayoutLogin,
                            "Error en los datos ingresados. Intente nuevamente",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    Resource.Status.SUCCESS -> {
                        Timber.d("status: ${it.status}")
                        Timber.d("data: ${it.data}")
                        navController.navigate(R.id.main_user)
                    }
                }
            })
        }
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}