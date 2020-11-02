package com.app.travelbuddy.data.remote.interceptor

import com.app.travelbuddy.data.local.dao.UserDao
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthenticationInterceptor(private val userDao: UserDao) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        try {
            val userLogin = userDao.getUsersSync().first()
            requestBuilder.addHeader("Authorization", "Bearer ${userLogin.user.jwt}")
        } catch (exception: Exception) {
            Timber.d("No user login")
        }

        return chain.proceed(requestBuilder.build())
    }
}