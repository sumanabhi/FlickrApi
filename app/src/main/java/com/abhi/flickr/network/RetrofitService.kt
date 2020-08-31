package com.abhi.flickr.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitService {
    companion object {

        var gson = GsonBuilder()
            .create()

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/services/rest/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        @JvmStatic
        fun <S> cteateService(serviceClass: Class<S>?): S {
            return retrofit.create(serviceClass)
        }
    }
}