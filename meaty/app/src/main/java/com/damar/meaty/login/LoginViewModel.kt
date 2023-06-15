package com.damar.meaty.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damar.meaty.api.ApiConfig
import com.damar.meaty.home.HomeFragment
import com.damar.meaty.response.LoginResponse
import com.damar.meaty.response.LoginResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    private val _infoError = MutableLiveData<Boolean>()
    val infoError: LiveData<Boolean> = _infoError

    private val _userInfo = MutableLiveData<LoginResult>()
    val userInfo: LiveData<LoginResult> = _userInfo

    fun checkLogin(username: String, password: String) {
        println("Username: $username")
        println("Password: $password")

        val client = ApiConfig.getApiService().userLogin(username, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _infoError.value = response.isSuccessful.not()
                _userInfo.value = response.body()?.loginResult

                // Simpan userId ke UserSession setelah mendapatkan respons
                val userId = response.body()?.loginResult?.userId?.toIntOrNull()
                HomeFragment.UserSession.userId = userId ?: 0
                println("User ID: ${HomeFragment.UserSession.userId}")

                println("info: ${_infoError.value}")
                println("Response Code: ${response.code()}")
                println("Response Message: ${response.message()}")
                println("Response Body: ${response.body()}")

                println("info: ${_infoError.value}")
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("MainActivity", "onFailure: ${t.message}")
            }
        })

    }
}