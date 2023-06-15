package com.damar.meaty.addscan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damar.meaty.api.ApiConfig
import com.damar.meaty.response.UploadResponse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddViewModel : ViewModel() {
    private val _isInfoError = MutableLiveData<Boolean>()
    val isInfoError: LiveData<Boolean> = _isInfoError

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun postImage(token: String, notes: String, imageFile: File) {
        val apiService = ApiConfig.getApiService()

        val notesBody = notes.toRequestBody("text/plain".toMediaTypeOrNull())
        val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, imageRequestBody)

        val storyCall = apiService.addImage(token, notesBody, imagePart)

        storyCall.enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                Log.e("AddViewModel", "Failed to post story: ${t.message}")
            }

            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
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
                println("Response umum: ${response}")
                println("Response Code: ${response.code()}")
                println("Response Message: ${response.message()}")
                println("Response: ${response.body()}")
            }
        })
    }
}