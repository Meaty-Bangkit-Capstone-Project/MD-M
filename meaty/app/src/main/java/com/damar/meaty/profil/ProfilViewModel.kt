package com.damar.meaty.profil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damar.meaty.response.LoginResult

class ProfilViewModel : ViewModel() {
    private val _user = MutableLiveData<LoginResult>()
    val user: LiveData<LoginResult> get() = _user

    fun setUser(loginResult: LoginResult) {
        _user.value = loginResult
    }
}

