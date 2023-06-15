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

    //        "username": "haloaja",
//        "password": "haloaja",
//        "email": "haloaja@example.com",
//        "first_name": "Halo",
//        "last_name": "Aja",
//        "domisili": "Jakarta",
//        "pekerjaan": "CFO",
//        "usia": 20,
//        "gender": "P"
//    }

    fun createUser(pUsername: String, pPassword: String, pEmail: String, pFirstName: String, pLastName: String, pDomisili: String, pPekerjaan: String, pUsia: Int, pGender: String) {
        val client = ApiConfig.getApiService().userRegister(pUsername, pPassword, pEmail, pFirstName, pLastName, pDomisili, pPekerjaan, pUsia, pGender)
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