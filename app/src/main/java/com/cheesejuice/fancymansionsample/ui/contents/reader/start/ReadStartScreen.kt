package com.cheesejuice.fancymansionsample.ui.contents.reader.start

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ReadStartScreen(
    onClickReadBookStart: () -> Unit
) {
    Column{
        Text(text = "ReadStartScreen!")
        Text(text = "Loading")
        Button(onClick = onClickReadBookStart) {

        }
    }

}

@Preview
@Composable
fun PreviewGreeting() {
    ReadStartScreen({})
}