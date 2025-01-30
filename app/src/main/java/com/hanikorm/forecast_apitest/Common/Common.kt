package com.hanikorm.forecast_apitest.Common

import com.hanikorm.forecast_apitest.Interface.RetrofitServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Common {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val retrofitService: RetrofitServices
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitServices::class.java)
}