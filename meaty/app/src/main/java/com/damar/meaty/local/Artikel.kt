package com.damar.meaty.local

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artikel(
    val judul: String,
    val kategori: String,
    val gambarUrl: String,
    val url: String
) : Parcelable