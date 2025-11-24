package com.jesen.personal.comment.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit 客户端单例，避免重复创建
 */
object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // 懒加载 Retrofit 实例
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 提供 ApiService 实例
    val apiService: ApiService by lazy {
        retrofit.create<ApiService>(ApiService::class.java)
    }
}