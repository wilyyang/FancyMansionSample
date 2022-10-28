package com.cheesejuice.fancymansionsample.ui.contents.reader.start

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ReadStartScreen(
    onClickReadBookStart: () -> Unit,
    viewModel: ReadStartViewModel = hiltViewModel()
) {
    viewModel.initConfig(12345)
    if(viewModel.loading.value == true){
        Column{
            Text(text = "Loading")
        }
    }else{
        Column{
            Text(text = "ReadStartScreen!")
            Button(onClick = onClickReadBookStart) {

            }
        }
    }
}

@Preview
@Composable
fun PreviewGreeting() {
    ReadStartScreen({})
}