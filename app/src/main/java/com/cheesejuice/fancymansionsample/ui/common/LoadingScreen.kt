package com.cheesejuice.fancymansionsample.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingScreen(
    loadingText: String = ""
) {
    Column{
        CircularProgressIndicator()
        if(loadingText != ""){
            Text(text = loadingText)
        }
    }
}


@Preview
@Composable
fun PreviewScreen() {
    LoadingScreen("대기중")
}