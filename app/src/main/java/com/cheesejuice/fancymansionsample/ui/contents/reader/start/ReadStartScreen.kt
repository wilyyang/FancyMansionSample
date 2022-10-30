package com.cheesejuice.fancymansionsample.ui.contents.reader.start

import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ReadStartScreen(
    viewModel: ReadStartViewModel = hiltViewModel(),
    onClickReadBookStart: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    viewModel.initConfig(12345)
    ReadStartScreenStateless(state, onClickReadBookStart)
}

@Composable
fun ReadStartScreenStateless(
    state: ReadStartViewModel.ReadStartUiState,
    onClickReadBookStart: () -> Unit = {}
) {
    Column{
        when(state) {
            is ReadStartViewModel.ReadStartUiState.Loaded -> {
                Text(text = state.config.title)
                Button(onClick = { state.deleteBookPref() }){}
                Button(onClick = onClickReadBookStart){}
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