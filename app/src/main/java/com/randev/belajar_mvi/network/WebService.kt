package com.randev.belajar_mvi.network

import com.randev.belajar_mvi.data.UserDetailResponse
import com.randev.belajar_mvi.data.UserResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Raihan Arman
 * @date 07/08/22
 */

interface WebService{

    @GET(EndPoint.GET_USER)
    suspend fun getList(
        @Query("page") page: Int
    ): Response<UserResponse>

    @GET(EndPoint.GET_DETAIL_USER)
    suspend fun getDetail(
        @Path("id") id: String
    ): Response<UserDetailResponse>

    companion object{
        private const val BASE_URL = "https://reqres.in"
        fun build(): WebService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebService::class.java)
        }
    }

    object EndPoint {
        const val GET_USER = "/api/users"
        const val GET_DETAIL_USER = "/api/users{id}"
    }

}