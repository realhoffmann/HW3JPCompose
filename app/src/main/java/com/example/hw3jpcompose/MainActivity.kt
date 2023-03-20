package com.example.hw3jpcompose

import android.content.Intent
import android.icu.text.CaseMap
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
        NewsItem(modifier = Modifier.fillMaxSize(), names = getNews())
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
            Column(modifier = with(Modifier) {
                weight(1f)
                        .scrollable(orientation = Orientation.Vertical, state = rememberScrollState())
            }
            ) {
                Text(text = name)
            }

            ElevatedButton(
                onClick = {
                    Intent(mContext, MainActivity2::class.java).apply {
                        putExtra("newsItem",name)
                    }
                mContext.startActivity(Intent(mContext, MainActivity2::class.java))

                }
            ) {
                Text(text = "Details")
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    HW3JPComposeTheme {

    }
}

@Preview
@Composable
fun MyAppPreview() {
    HW3JPComposeTheme {
        MyApp(Modifier.fillMaxSize())
    }
}