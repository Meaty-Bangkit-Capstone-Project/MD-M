package com.damar.meaty.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.damar.meaty.api.ApiService
import com.damar.meaty.response.Story

class StoryRepository(private val apiService: ApiService, private val token: String) {

    fun getStory(): LiveData<PagingData<Story>> {
        val pagingSourceFactory = { StoryPaging(apiService, token) }

        val pager = Pager(
            config = PagingConfig(pageSize = 10), pagingSourceFactory = pagingSourceFactory
        )

        return pager.liveData
    }
}