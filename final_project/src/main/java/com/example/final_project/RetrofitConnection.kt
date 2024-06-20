package com.example.final_project

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit

object RetrofitConnection {
    private const val BASE_URL = "http://apis.data.go.kr/1360000/TourStnInfoService1/"

    private val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(TikXmlConverterFactory.create(parser))
        .build()

    val xmlNetworkService: NetworkService = retrofit.create(NetworkService::class.java)
}
