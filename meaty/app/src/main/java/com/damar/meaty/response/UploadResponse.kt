package com.damar.meaty.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(

    @field:SerializedName("result")
    val result: Boolean? = null,

    @field:SerializedName("prediction")
    val prediction: String? = null
)