package com.example.hw3jpcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hw3jpcompose.ui.theme.HW3JPComposeTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HW3JPComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    DetailedItem()
                }
            }
        }
    }
}

@Composable
fun DetailedItem() {
    val mContext = LocalContext.current
    Row(modifier = Modifier.padding(24.dp)) {

            Button(
                onClick = { mContext.startActivity(Intent(mContext, MainActivity::class.java)) }
            ) {
                Text(text = "Back")
            }

        Column {
                Text(getNewsSingle("0"))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    HW3JPComposeTheme {
        DetailedItem()
    }
}