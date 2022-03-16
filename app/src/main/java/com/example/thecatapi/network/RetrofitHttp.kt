package com.example.pinterest.network

import com.example.pinterest.network.service.PhotoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttp {
    private const val IS_TESTER = true
    private const val SERVER_DEVELOPMENT = "https://api.thecatapi.com/v1/"
    private const val SERVER_PRODUCTION = "https://api.thecatapi.com/v1/"

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun server(): String {
        if (IS_TESTER)
            return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    val photoService: PhotoService = retrofit.create(PhotoService::class.java)
}