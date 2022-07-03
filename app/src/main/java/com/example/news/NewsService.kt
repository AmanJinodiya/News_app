package com.example.news


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=0133b44f99974dcd86ec4100a79aebb3

const val base_url = "https://newsapi.org/"
const val api_key ="0133b44f99974dcd86ec4100a79aebb3"

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$api_key")
    fun getheadlines(@Query("country")country: String, @Query("page")page : Int) :Call<NewsJsonData>
}

object NewsService{
    val newsInstance: NewsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }

}


