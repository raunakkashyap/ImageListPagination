package com.example.imagelistpagination.model

import com.google.gson.annotations.SerializedName

data class ImageDetailResponse (

    @SerializedName("id") var id : String,
    @SerializedName("author") var author : String,
    @SerializedName("width") var width : Int,
    @SerializedName("height") var height : Int,
    @SerializedName("url") var url : String,
    @SerializedName("download_url") var downloadUrl : String

)
