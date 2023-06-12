package com.damar.meaty.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.damar.meaty.api.ApiService
import com.damar.meaty.response.Story

class StoryPaging(private val apiService: ApiService, private val token: String) :
    PagingSource<Int, Story>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getAllStory(token, position, params.loadSize)
            val data = response.listStory

            LoadResult.Page(
                data = data,
                prevKey = when {
                    position == INITIAL_PAGE_INDEX -> null
                    else -> position - 1
                },
                nextKey = when {
                    data.isNullOrEmpty() -> null
                    else -> position + 1
                }
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            when {
                anchorPage != null -> anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
                else -> null
            }
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
