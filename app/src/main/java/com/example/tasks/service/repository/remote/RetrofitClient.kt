package com.example.tasks.service.repository.remote

import com.example.tasks.service.constants.TaskConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {
        private lateinit var retrofit: Retrofit
        private val baseurl = "http://devmasterteam.com/CursoAndroidAPI/"

        private var mPersonKey = ""
        private var mTokenKey = ""


        private fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request =
                        chain.request()
                            .newBuilder()
                            .addHeader(TaskConstants.HEADER.PERSON_KEY, mPersonKey)
                            .addHeader(TaskConstants.HEADER.TOKEN_KEY, mTokenKey)
                            .build()
                    return chain.proceed(request)
                }
            })

            if (!Companion::retrofit.isInitialized)
                retrofit = Retrofit.Builder()
                    .baseUrl(baseurl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit
        }

        fun addHeader(token: String, personKey: String) {
            this.mPersonKey = personKey
            this.mTokenKey = token
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)
        }
    }
}