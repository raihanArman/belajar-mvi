package com.randev.belajar_mvi.data

import com.google.gson.annotations.SerializedName

/**
 * @author Raihan Arman
 * @date 07/08/22
 */

data class UserDetailResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("support")
    val support: Support?
) {
    data class Data(
        @SerializedName("avatar")
        val avatar: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("first_name")
        val firstName: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("last_name")
        val lastName: String?
    )

    data class Support(
        @SerializedName("text")
        val text: String?,
        @SerializedName("url")
        val url: String?
    )
}