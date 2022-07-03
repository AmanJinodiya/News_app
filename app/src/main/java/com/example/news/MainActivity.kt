package com.example.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.littlemango.stacklayoutmanager.StackLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<NewsArticle>()
    var totalresults : Int = -1
    var pagenum = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recycler = findViewById<RecyclerView>(R.id.recyclerview)
        adapter = NewsAdapter(this@MainActivity,articles)
        recycler.adapter = adapter


        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.RIGHT_TO_LEFT)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)

        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener{
            override fun onItemChanged(position: Int) {
                Log.d("Aman","First visible item ${layoutManager.getFirstVisibleItemPosition()}")
                Log.d("Aman","Item count - ${layoutManager.itemCount}")

                if((totalresults > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount - 5) || layoutManager.itemCount == layoutManager.getFirstVisibleItemPosition()+1)
                {
                    if(layoutManager.itemCount == layoutManager.getFirstVisibleItemPosition()+1)
                    {
                        pagenum = 1
                        getnews()
                    }
                    else
                    {
                        pagenum++
                        getnews()
                    }

                }
            }

        })

        recycler.layoutManager = layoutManager
        getnews()
    }

    private fun getnews() {
        Log.d("Aman","request sent for data $pagenum")
        val news = NewsService.newsInstance.getheadlines("IN",pagenum)
        news.enqueue(object : Callback<NewsJsonData>{
            override fun onResponse(call: Call<NewsJsonData>, response: Response<NewsJsonData>) {
                Log.d("Aman","Success")
                val newsJsonData = response.body()
                if(newsJsonData!= null)
                {
                    totalresults = newsJsonData.totalResults
                    articles.addAll(newsJsonData.articles)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<NewsJsonData>, t: Throwable) {
                Log.d("Aman","Error in api",t)
            }

        })
    }
}