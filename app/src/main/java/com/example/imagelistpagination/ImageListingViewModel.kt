package com.example.imagelistpagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagelistpagination.model.ImageDetailResponse
import com.example.imagelistpagination.model.ImageListResponse
import com.example.imagelistpagination.network.ApiService
import com.example.imagelistpagination.repository.ImageRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ImageListingViewModel(private val repository: ImageRepository) : ViewModel(), KoinComponent {

    private val _imageList = MutableLiveData<List<ImageListResponse>>()
    val imageList: LiveData<List<ImageListResponse>> get() = _imageList

    private val _imageDetail = MutableLiveData<ImageDetailResponse>()
    val imageDetails: LiveData<ImageDetailResponse> get() = _imageDetail

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var currentPage = 1
    private val pageSize = 30

    init {
        fetchImageList()
    }

    fun getCurrentPage(): Int {
        return currentPage
    }

    fun loadNextPage() {
        currentPage++
        fetchImageList()
    }

    private fun fetchImageList() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val newList = repository.getImageList(currentPage, pageSize)
                _imageList.value = newList
                currentPage++
            } catch (e: Exception) {
                // Handle error
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchImageDetail(imageId: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val detail = repository.getImageDetail(imageId)
                _imageDetail.value = detail
            } catch (e: Exception) {
                // Handle error
            } finally {
                _loading.value = false
            }
        }
    }
}

