package com.damar.meaty.addscan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damar.meaty.api.ApiConfig
import com.damar.meaty.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddViewModel : ViewModel() {
    private val _isInfoError = MutableLiveData<Boolean>()
    val isInfoError: LiveData<Boolean> = _isInfoError

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun postStory(token: String, desc: RequestBody, photo: MultipartBody.Part) {
//    fun postStory(token: String, photo: MultipartBody.Part) {
        val apiService = ApiConfig.getApiService()
        val storyCall = apiService.addStory(token, desc, photo)

        storyCall.enqueue(object : Callback<RegisterResponse> {
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("AddViewModel", "Failed to post story: ${t.message}")
            }

            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        _isInfoError.value = false
                    }
                    else -> {
                        _isInfoError.value = true
                        _errorMessage.value = response.message()
                    }
                }
            }
        })
    }
}