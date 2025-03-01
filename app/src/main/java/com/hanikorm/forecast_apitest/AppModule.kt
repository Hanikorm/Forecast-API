package com.hanikorm.forecast_apitest

import com.hanikorm.data.network.RetrofitServices
import com.hanikorm.data.repository.WeatherRepositoryImpl
import com.hanikorm.domain.WeatherRepository
import com.hanikorm.domain.usecase.GetForecastUseCase
import com.hanikorm.domain.usecase.GetWeatherUseCase
import com.hanikorm.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<RetrofitServices> { get<Retrofit>().create(RetrofitServices::class.java) }

    single<WeatherRepository> { WeatherRepositoryImpl(get()) }

    single { GetWeatherUseCase(get()) }
    single { GetForecastUseCase(get()) }

    viewModel { WeatherViewModel(get(), get()) }
}
