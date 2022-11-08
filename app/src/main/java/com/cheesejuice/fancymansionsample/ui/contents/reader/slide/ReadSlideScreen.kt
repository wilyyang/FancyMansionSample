package com.cheesejuice.fancymansionsample.ui.contents.reader.slide

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cheesejuice.fancymansionsample.Const
import com.cheesejuice.fancymansionsample.ui.common.*
import com.cheesejuice.fancymansionsample.ui.theme.*

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
        if(state.slide.slideImage != ""){
            SelectiveImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                imageFile = state.slideImage)
        }
        DividerRow()
        LazyColumn (modifier = Modifier
            .weight(1f)
            .padding(horizontal = 20.dp)){
            item{
                Column(modifier = Modifier.padding(vertical = 20.dp)) {
                    if(state.slideType == Const.SLIDE_TYPE_END){
                        TypeText(text="엔딩")
                    }
                    Text(text = state.slide.slideTitle, style = ReaderTitleStyle)
                }
                Text(modifier = Modifier.padding(bottom = 20.dp), text = state.slide.description, style = ReaderScriptStyle)
                Text(modifier = Modifier.padding(bottom = 20.dp), text = state.slide.question, style = ReaderSubTitleStyle)
            }

            items(state.passChoiceItems) { item ->
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 8.dp),
                    contentPadding = PaddingValues(13.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SlideButtonPink
                    ),
                    shape = RoundedCornerShape(15),
                    onClick = { state.moveToNextSlide(item) })
                {
                    Text(modifier = Modifier.fillMaxWidth(), text = item.title, style = SlideButtonStyle)
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