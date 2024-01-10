package com.panasetskaia.hedvigtestgithubapi.data.remote

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

const val BASE_URL = "https://api.github.com/"

class Networking @Inject constructor() {
    private var okhttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    private val contentType = "application/json".toMediaType()

    private val json = Json { ignoreUnknownKeys = true }

    val githubApi: GithubApi
        get() = retrofit?.create(GithubApi::class.java) ?: error("retrofit is not initialized")

    init {
        okhttpClient = OkHttpClient.Builder().build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient!!)
            .addConverterFactory(
                json
                    .asConverterFactory(contentType)
            )
            .build()
    }
}