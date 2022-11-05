package com.cheesejuice.fancymansionsample.ui.contents.reader.slide

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cheesejuice.fancymansionsample.ui.common.DividerRow
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
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()){
        Column(modifier = Modifier.weight(weight = 1.0f, true)){
            if(state.slide.slideImage != ""){
                SelectiveImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp),
                    imageFile = state.slideImage)
            }
            DividerRow()
            LazyColumn (modifier =  Modifier.weight(1f)){
                item{
                    Text(text = state.slide.slideTitle)
                    Text(text = state.slide.description)

                }

                items(state.passChoiceItems) { item ->
                    Button(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
                        shape = RoundedCornerShape(15),
                        onClick = {
                            state.moveToNextSlide(item)
                        }){ Text(text = item.title, textAlign = TextAlign.Start)}
                }
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