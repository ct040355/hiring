package com.kma.movies.data.sources.remote.api

import com.kma.movies.data.sources.remote.CommonSharedPreferences
import com.kma.movies.data.sources.remote.auth.MoviesAuthInterceptor
import com.kma.movies.data.sources.remote.service.MovieService
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy


object ApiClient {

    private const val BASE_URL = "http://kma.bcastmirror.com"
    const val POSTER_BASE_URL = "http://kma.bcastmirror.com"

    private val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(MoviesAuthInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .build()
    val cookieJar: CookieJar = object : CookieJar {
        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            CommonSharedPreferences.getInstance().setCookie(cookies)
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val cookies = CommonSharedPreferences.getInstance().getCookie()
            return cookies ?: ArrayList()
        }
    }

    private fun retrofit(): Retrofit {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .cookieJar(cookieJar)
                    .build()
            )
            .build()
    }

    fun movieService(): MovieService = retrofit().create(
        MovieService::class.java
    )

}