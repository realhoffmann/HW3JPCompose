package com.example.hw3jpcompose

import android.content.Intent
import android.icu.text.CaseMap
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hw3jpcompose.ui.theme.HW3JPComposeTheme
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HW3JPComposeTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        NewsItem(modifier = Modifier.fillMaxSize(), names = listOf("Disco Elysium’s Collage Mode allows you to write new dialogue", "News 2", "News 3"))
        }
    }

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    names: List<String>
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            SeeDetails(name = name)
        }
    }
}



@Composable
private fun SeeDetails(name: String) {

    val mContext = LocalContext.current

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
            ) {
                Text(text = name)
            }
            ElevatedButton(
                onClick = { mContext.startActivity(Intent(mContext, MainActivity2::class.java))}
            ) {
                Text(text = "Details")
            }
        }
    }
}

private fun getContentFromWeb(): FetchNewsResult {
    return try {
        val url = URL("https://www.engadget.com/rss.xml")
        (url.openConnection() as HttpURLConnection).run {
            requestMethod = "GET"
            connectTimeout = 5000
            readTimeout = 5000
            String(inputStream.readBytes())
        }.let { Success(it) }
    } catch (ioException: IOException) {
        Failed("Error while fetching news", ioException)
    }
}

sealed class FetchNewsResult
class Success(val result: String) : FetchNewsResult()
class Failed(val text: String, val throwable: Throwable) : FetchNewsResult()


@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    HW3JPComposeTheme {
        //NewsItem()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    HW3JPComposeTheme {
        MyApp(Modifier.fillMaxSize())
    }
}