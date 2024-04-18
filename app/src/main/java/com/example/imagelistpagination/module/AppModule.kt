package com.example.imagelistpagination.module

import com.example.imagelistpagination.ImageListingViewModel
import com.example.imagelistpagination.network.ApiService
import com.example.imagelistpagination.repository.ImageRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val imageModule = module {
    single { provideRetrofit().create(ApiService::class.java) }
    single { ImageRepository(get()) }
    viewModel { ImageListingViewModel(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://picsum.photos/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideOkHttpClient())
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()
}

