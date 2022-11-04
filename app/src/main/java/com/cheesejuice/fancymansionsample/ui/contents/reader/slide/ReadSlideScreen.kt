package com.cheesejuice.fancymansionsample.ui.contents.reader.slide

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.cheesejuice.fancymansionsample.ui.common.EmptyScreen
import com.cheesejuice.fancymansionsample.ui.common.LoadingScreen
import com.cheesejuice.fancymansionsample.ui.common.SelectiveImage

@Composable
fun ReadSlideScreen(
    viewModel: ReadSlideViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    ReadSlideScreenWithState(state)
}

@Composable
fun ReadSlideScreenWithState(
    state: ReadSlideViewModel.ReadSlideUiState
) {
    when(state) {
        is ReadSlideViewModel.ReadSlideUiState.Loaded -> {
            ReadSlideScreenLoaded(state)
        }
        is ReadSlideViewModel.ReadSlideUiState.Loading -> {
            LoadingScreen()
        }
        is ReadSlideViewModel.ReadSlideUiState.Empty -> {
            EmptyScreen()
        }
    }
}

@Composable
fun ReadSlideScreenLoaded(
    state: ReadSlideViewModel.ReadSlideUiState.Loaded
) {
    Column {
        Log.e("WILY9", ""+state.slide)
        if(state.slide.slideImage != ""){
            SelectiveImage(imageFile = state.slideImage)
        }

        Text(text = state.slide.slideTitle)
        Text(text = state.slide.description)

        LazyColumn {
            items(state.passChoiceItems) { item ->
                Button(
                    onClick = {
                    state.moveToNextSlide(item)
                }){ Text(text = item.title)}
            }
        }
    }

    if(state.isSlideMove.value){
        LoadingScreen(alpha = 0.3f)
    }
}

@Preview
@Composable
fun PreviewScreen() {
    ReadSlideScreenWithState(ReadSlideViewModel.ReadSlideUiState.Empty)
}