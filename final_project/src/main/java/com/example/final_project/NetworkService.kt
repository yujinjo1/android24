package com.example.final_project

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("getCityTourClmIdx1")
    fun getXmlList(
        @Query("serviceKey") apiKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("dataType") dataType: String,
        @Query("CURRENT_DATE") currentDate: String,
        @Query("DAY") day: String
    ): Call<XmlResponse>
}
