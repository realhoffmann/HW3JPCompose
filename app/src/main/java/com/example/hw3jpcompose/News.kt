package com.example.hw3jpcompose

import android.content.Intent
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.compose.ui.platform.LocalContext
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory


/**
 * Data class for the news and its underlying data
 * @author Altin
 * @version 1.0
 * @since 2023-03-19
 */
data class NewsItem(
    var id: Int,
    var title: String,
    var description: String,
    var url: String,
    var author: String,
    var publicationDate: String,
    var fullArticleLink: String,
    var keywords: List<String>
)

fun newsItemToString(newsItem: NewsItem): String {
    return  "Id: ${newsItem.id} \n" +
            "Title: ${newsItem.title} \n " +
            "Description: ${newsItem.description} \n" +
            "Url: ${newsItem.url} \n" +
            "Author: ${newsItem.author} \n" +
            "PublicationDate: ${newsItem.publicationDate} \n" +
            "FullArticleLink: ${newsItem.fullArticleLink} \n" +
            "Keywords: ${newsItem.keywords} \n"
}

/**
 * Converts a list of XML news to a string
 */

fun parseXmlNews(result: String): List<NewsItem> {
    val factory = DocumentBuilderFactory.newInstance()
    val builder = factory.newDocumentBuilder()
    val xml = builder.parse(result.byteInputStream())

    val newsItems = mutableListOf<NewsItem>()
    val news = xml.getElementsByTagName("item")
    for (i in 0 until news.length) {
        val newsItem = news.item(i)
        val title = newsItem.childNodes.item(0).textContent
        val description = newsItem.childNodes.item(1).textContent
        val url = newsItem.childNodes.item(18)?.attributes?.getNamedItem("url")?.nodeValue.toString().trim()
        val author = newsItem.childNodes.item(5).textContent
        val publicationDate = newsItem.childNodes.item(9).textContent
        val fullArticleLink = newsItem.childNodes.item(2).textContent
        val keywords = newsItem.childNodes.item(12).textContent.split(",").map { it.trim() }
        newsItems.add(
            NewsItem(i+1, title, description, url, author, publicationDate, fullArticleLink, keywords)
        )
    }
    return sortNewsByPublicationDate(newsItems)
}

/**
 * Takes a mutable list of news items and sorts them by publication date in descending order
 * since the highest id is the most recent news item
 * @param news the list of news items
 * @return the sorted list
 */
fun sortNewsByPublicationDate(news: MutableList<NewsItem>): List<NewsItem> {
    news.sortByDescending { it.id}
    return news
}

 fun getContentFromWeb(): String  {
     val policy = ThreadPolicy.Builder().permitAll().build()
     StrictMode.setThreadPolicy(policy)

    val url = URL("https://www.engadget.com/rss.xml")
    val connection = url.openConnection() as HttpURLConnection
    try {
        val resultAsString = connection.run {
            requestMethod = "GET"
            connectTimeout = 5000
            readTimeout = 5000
            String(inputStream.readBytes())
        }
        return resultAsString
    } finally {
        connection.disconnect()
    }
}

fun getNews(): List<String> {
    val result = getContentFromWeb()
    val newsList =  parseXmlNews(result)
    return newsList.map { it.title }
}

fun getNewsSingle(name:String): String {
    val result = getContentFromWeb()
    val newsList =  parseXmlNews(result)
    val newsItem = newsList.find { it.title == name} ?: NewsItem(0, "", "", "", "", "", "", listOf())
    return newsItemToString(newsItem)
}

