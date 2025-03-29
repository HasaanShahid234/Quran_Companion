package com.example.qurancompanion.data

import com.example.qurancompanion.response_model.QuranResponse
import com.example.qurancompanion.utils.RetrofitInstance
import retrofit2.Response

class QuranRepository {
    suspend fun getQuran(): Response<QuranResponse> {
        return RetrofitInstance.api.getQuran()
    }
}