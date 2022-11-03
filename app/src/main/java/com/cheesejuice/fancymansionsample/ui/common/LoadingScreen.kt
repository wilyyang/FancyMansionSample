package com.cheesejuice.fancymansionsample.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadingScreen(
    loadingText: String = "",
    alpha:Float = 0f
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black.copy(alpha = alpha)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Spacer(modifier = Modifier.height(20.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(60.dp)
            )
            if(loadingText != ""){
                Text(
                    modifier = Modifier.padding(top = 40.dp),
                    text = loadingText,
                    fontSize = 14.sp)
            }
        }
    }

}


@Preview
@Composable
fun PreviewLoadingScreen() {
    LoadingScreen("대기중")
}