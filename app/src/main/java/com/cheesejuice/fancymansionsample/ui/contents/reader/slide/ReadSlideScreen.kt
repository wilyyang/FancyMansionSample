package com.cheesejuice.fancymansionsample.ui.contents.reader.slide

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.cheesejuice.fancymansionsample.ui.common.LoadingScreen

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
    Column{
        when(state) {
            is ReadSlideViewModel.ReadSlideUiState.Loaded -> {
                val context = LocalContext.current
                val imageLoader = ImageLoader.Builder(context)
                    .components {
                        if (Build.VERSION.SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()

                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context).data(data = state.slideImage).apply(block = {
                            size(Size.ORIGINAL)
                        }).build(), imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(text = state.slide.slideTitle)
                Text(text = state.slide.description)

                LazyColumn {
                    items(state.passChoiceItems) { item ->
                        Button(onClick = {
                            state.moveToNextSlide(item)
                        }){ Text(text = item.title)}
                    }
                }
            }
            is ReadSlideViewModel.ReadSlideUiState.Loading -> {
                LoadingScreen()
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
    ReadSlideScreenWithState(ReadSlideViewModel.ReadSlideUiState.Empty)
}