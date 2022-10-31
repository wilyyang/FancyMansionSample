package com.cheesejuice.fancymansionsample.ui.contents.reader.slide

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ReadSlideScreen(
    viewModel: ReadSlideViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    ReadSlideScreenStateless(state)
}

@Composable
fun ReadSlideScreenStateless(
    state: ReadSlideViewModel.ReadSlideUiState
) {
    Column{
        when(state) {
            is ReadSlideViewModel.ReadSlideUiState.Loaded -> {
                Text(text = state.slide.description)
            }
            is ReadSlideViewModel.ReadSlideUiState.Loading -> {
                Text(text = "ReadSlideScreen 로딩 중")
            }
            is ReadSlideViewModel.ReadSlideUiState.Empty -> {
                Text(text = "ReadSlideScreen Empty")
            }
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {
    ReadSlideScreenStateless(ReadSlideViewModel.ReadSlideUiState.Empty)
}