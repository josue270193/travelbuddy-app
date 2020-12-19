package com.app.josuelopez.travelbuddy.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.josuelopez.travelbuddy.data.local.entity.UserFull
import com.app.josuelopez.travelbuddy.data.repository.UserRepository
import com.app.josuelopez.travelbuddy.utils.Resource

class UserViewModel @ViewModelInject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    fun getUserLogin(): LiveData<Resource<UserFull>> {
        return userRepository.getUserLogin()
    }

    fun signIn(email: String, password: String): LiveData<Resource<UserFull>> {
        return userRepository.signIn(email, password)
    }

    fun signUp(email: String, password: String, name: String): LiveData<Resource<UserFull>> {
        return userRepository.signUp(email, password, name)
    }
}