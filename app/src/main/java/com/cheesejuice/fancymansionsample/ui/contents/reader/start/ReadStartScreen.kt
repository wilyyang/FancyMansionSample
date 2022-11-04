package com.cheesejuice.fancymansionsample.ui.contents.reader.start

import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.cheesejuice.fancymansionsample.Const
import com.cheesejuice.fancymansionsample.R
import com.cheesejuice.fancymansionsample.data.models.Config
import com.cheesejuice.fancymansionsample.ui.common.CustomAlertDialog
import com.cheesejuice.fancymansionsample.ui.common.EmptyScreen
import com.cheesejuice.fancymansionsample.ui.common.LoadingScreen
import com.cheesejuice.fancymansionsample.ui.common.SelectiveImage

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
    val openDialog = remember { mutableStateOf(false)  }
    Column(Modifier.fillMaxWidth()){
        SelectiveImage(imageFile = state.coverImage)

        Text(text = state.config.title)
        Button(onClick = {
            if(state.getSaveSlideId() != Const.ID_NOT_FOUND){
                openDialog.value = true
            }else{
                state.deleteBookPref()
                onClickReadBookStart(state.config.bookId, Const.FIRST_SLIDE)
            }
        }){}

        if (openDialog.value) {
            CustomAlertDialog(title = stringResource(id = R.string.record_dialog_title),
                text = stringResource(id = R.string.record_dialog_text),
                confirmAction = {
                    openDialog.value = false
                    onClickReadBookStart(state.config.bookId, state.getSaveSlideId())
                },
                dismissAction = {
                    openDialog.value = false
                    state.deleteBookPref()
                    onClickReadBookStart(state.config.bookId, Const.FIRST_SLIDE)
                })
        }
    }
}


@Preview
@Composable
fun PreviewScreen() {
    ReadStartScreenLoaded(ReadStartViewModel.ReadStartUiState.Loaded(Config(), null, {0}, {}))
}