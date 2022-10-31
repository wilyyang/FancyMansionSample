package com.cheesejuice.fancymansionsample.ui.contents.reader.start

import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ReadStartScreen(
    viewModel: ReadStartViewModel = hiltViewModel(),
    onClickReadBookStart: (bookId:Long, slideId:Long) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    ReadStartScreenStateless(state, onClickReadBookStart)
}

@Composable
fun ReadStartScreenStateless(
    state: ReadStartViewModel.ReadStartUiState,
    onClickReadBookStart: (bookId:Long, slideId:Long) -> Unit = { _, _ -> }
) {
    Column{
        when(state) {
            is ReadStartViewModel.ReadStartUiState.Loaded -> {
                Text(text = state.config.title)
                Button(onClick = { state.deleteBookPref() }){}
                Button(onClick = {
                    onClickReadBookStart(state.config.bookId, 200000000L) }){}
            }
            is ReadStartViewModel.ReadStartUiState.Loading -> {
                Text(text = state.message)
            }
            is ReadStartViewModel.ReadStartUiState.Empty -> {
                Text(text = "ReadStartScreen Empty")
            }
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {
    ReadStartScreenStateless(ReadStartViewModel.ReadStartUiState.Empty)
}