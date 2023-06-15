package com.damar.meaty.api

import com.damar.meaty.response.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register/")
    fun userRegister(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("domisili") domisili: String,
        @Field("pekerjaan") pekerjaan: String,
        @Field("usia") usia: Int,
        @Field("gender") gender: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login/")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @Multipart
    @POST("upload/")
    fun addImage(
        @Header("Authorization") token: String,
        @Part("notes") notes: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<UploadResponse>

    @GET("history/{userId}/")
    fun getHistory(@Path("userId") userId: Int): Call<ArrayList<HistoryResponse>>

}