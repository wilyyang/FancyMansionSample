package com.cheesejuice.fancymansionsample.ui.contents.reader.start

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size.Companion.ORIGINAL
import com.cheesejuice.fancymansionsample.R
import com.cheesejuice.fancymansionsample.data.models.Config
import com.cheesejuice.fancymansionsample.ui.common.CustomAlertDialog
import com.cheesejuice.fancymansionsample.ui.common.EmptyScreen
import com.cheesejuice.fancymansionsample.ui.common.LoadingScreen

@Composable
fun ReadStartScreen(
    viewModel: ReadStartViewModel = hiltViewModel(),
    onClickReadBookStart: (bookId:Long, slideId:Long) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    ReadStartScreenWithState(state, onClickReadBookStart)
}

@Composable
fun ReadStartScreenWithState(
    state: ReadStartViewModel.ReadStartUiState,
    onClickReadBookStart: (bookId:Long, slideId:Long) -> Unit = { _, _ -> }
) {
    when(state) {
        is ReadStartViewModel.ReadStartUiState.Loaded -> {
            ReadStartScreenLoaded(state, onClickReadBookStart)
        }
        is ReadStartViewModel.ReadStartUiState.Loading -> {
            LoadingScreen(loadingText = state.message)
        }
        is ReadStartViewModel.ReadStartUiState.Empty -> {
            EmptyScreen()
        }
    }
}

@Composable
fun ReadStartScreenLoaded(
    state: ReadStartViewModel.ReadStartUiState.Loaded,
    onClickReadBookStart: (bookId:Long, slideId:Long) -> Unit = { _, _ -> }
) {
    Column(Modifier.fillMaxWidth()){
        val openDialog = remember { mutableStateOf(false)  }

        val context = LocalContext.current
        val imageLoader = ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(data = state.coverImage).apply(block = {
                    size(ORIGINAL)
                }).build(), imageLoader = imageLoader
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
        )

        Text(text = state.config.title)
        Button(onClick = {
            openDialog.value = true
        }){}

        if (openDialog.value) {
            CustomAlertDialog(title = stringResource(id = R.string.record_dialog_title),
            text = stringResource(id = R.string.record_dialog_text),
            confirmAction ={ openDialog.value = false; onClickReadBookStart(state.config.bookId, state.saveSlideId)},
            dismissAction = { openDialog.value = false; state.deleteBookPref(); onClickReadBookStart(state.config.bookId, state.saveSlideId) })
        }
    }
}


@Preview
@Composable
fun PreviewScreen() {
    ReadStartScreenLoaded(ReadStartViewModel.ReadStartUiState.Loaded(Config(), 100000L, null, {}))
}