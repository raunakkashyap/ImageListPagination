package com.example.imagelistpagination.repository

import com.example.imagelistpagination.model.ImageDetailResponse
import com.example.imagelistpagination.model.ImageListResponse
import com.example.imagelistpagination.network.ApiService

class ImageRepository(private val apiService: ApiService) {
    suspend fun getImageList(page: Int, limit: Int): List<ImageListResponse> {
        return apiService.getImageList(page, limit)
    }
    suspend fun getImageDetail(id: String): ImageDetailResponse {
        return apiService.getImageDetail(id)
    }
}