package com.damar.meaty.response

data class HistoryResponse(
    val image: String,
    val timestamp: String,
    val prediction: String,
    val notes: String
)
