package com.damar.meaty.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damar.meaty.api.ApiConfig
import com.damar.meaty.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val _infoError = MutableLiveData<Boolean>()
    val infoError: LiveData<Boolean> = _infoError

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun createUser(pName: String, pEmail: String, pPassword: String) {
        val client = ApiConfig.getApiService().userRegister(pName, pEmail, pPassword)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                when {
                    response.isSuccessful -> {
                        _infoError.value = false
                    }
                    else -> {
                        _errorMessage.value = response.message()
                        _infoError.value = true
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("RegisterActivity", "onFailure: ${t.message}")
            }
        })
    }
}