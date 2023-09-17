package com.example.data.dto.response

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("front_default")
    val frontDefault: String
)
