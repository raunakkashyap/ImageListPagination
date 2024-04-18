package com.example.imagelistpagination.network

import com.example.imagelistpagination.model.ImageDetailResponse
import com.example.imagelistpagination.model.ImageListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("v2/list")
    suspend fun getImageList(@Query("page") page: Int, @Query("limit") limit: Int): List<ImageListResponse>

    @GET("id/{id}/info")
    suspend fun getImageDetail(@Path("id") id: String): ImageDetailResponse

}