package com.damar.meaty.hasil

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.damar.meaty.paging.StoryRepository
import com.damar.meaty.response.Story

class HasilViewModel(storyRepository: StoryRepository) : ViewModel() {

    val story: LiveData<PagingData<Story>> =
        storyRepository.getStory()
            .cachedIn(viewModelScope)
}