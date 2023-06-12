package com.damar.meaty.response

import com.google.gson.annotations.SerializedName

data class StoriesResponse(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("listStory")
    val listStory: List<Story>
)
