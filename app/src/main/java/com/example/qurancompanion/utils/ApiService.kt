package com.example.qurancompanion.utils

import com.example.qurancompanion.response_model.QuranResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("quran/ar.alafasy")
    suspend fun getQuran(): Response<QuranResponse>
    @GET("quran/ar.alafasy")
    fun getQuranPak(): Call<QuranResponse>
}