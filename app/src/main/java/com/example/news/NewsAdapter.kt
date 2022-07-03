package com.example.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(var context:Context, var articles: List<NewsArticle>):
    RecyclerView.Adapter<NewsAdapter.articleViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): articleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        return articleViewHolder(view)
    }

    override fun onBindViewHolder(holder: articleViewHolder, position: Int) {
        val article = articles[position]
        holder.newsTittle.text = article.title
        holder.newsDescription.text = article.content
        val url = article.urlToImage
//        Glide.with(context).load(article.urlToImage).into(holder.newsImage)
        if(url != null)
        {
            Glide.with(context).load(url).into(holder.newsImage)
        }
        else{
            holder.newsImage.setImageResource(R.drawable.no_image)
        }

        holder.itemView.setOnClickListener{
            Toast.makeText(context,article.title,Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return articles.size
    }



    class articleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val newsImage = itemView.findViewById<ImageView>(R.id.news_image)
        val newsTittle = itemView.findViewById<TextView>(R.id.news_tittle)
        val newsDescription = itemView.findViewById<TextView>(R.id.news_description)
    }
}