package com.akinci.repolisting.commons.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        fun getClient(requestUrl : String) : Retrofit {

            /** RETROFIT REQUEST RESPONSE LOGGER
             *      CHANGE LEVEL PARAMETER AS YOU DESIRED
             *      PARAMS -> NONE-BODY-HEADERS-BASIC
             *  **/
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.NONE

            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.addInterceptor(logging)

            return Retrofit.Builder()
                .baseUrl(requestUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
        }
    }
}