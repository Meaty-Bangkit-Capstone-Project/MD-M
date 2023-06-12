package com.damar.meaty.api


import com.damar.meaty.home.HomeFragment
import com.damar.meaty.paging.StoryRepository

object Injection {
    fun provideRepository(): StoryRepository {
        val apiService = ApiConfig.getApiService()
        val userToken = HomeFragment.USER_TOKEN ?: ""
        return StoryRepository(apiService, userToken)
    }
}