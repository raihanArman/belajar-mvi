package com.randev.belajar_mvi.data


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("support")
    val support: Support,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)