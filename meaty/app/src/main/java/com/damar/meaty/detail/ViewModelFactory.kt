package com.damar.meaty.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.damar.meaty.api.Injection
import com.damar.meaty.home.HomeViewModel


//class ViewModelFactory : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return when {
//            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
//                @Suppress("UNCHECKED_CAST")
//                HomeViewModel(Injection.provideRepository()) as T
//            }
//            else -> throw IllegalArgumentException("Unknown ViewModel class")
//        }
//    }
//}