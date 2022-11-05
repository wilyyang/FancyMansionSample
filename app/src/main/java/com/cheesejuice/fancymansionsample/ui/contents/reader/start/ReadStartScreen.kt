package com.cheesejuice.fancymansionsample.ui.contents.reader.start

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cheesejuice.fancymansionsample.Const
import com.cheesejuice.fancymansionsample.R
import com.cheesejuice.fancymansionsample.data.models.Config
import com.cheesejuice.fancymansionsample.ui.common.*

@Composable
fun ReadStartScreen(
    viewModel: ReadStartViewModel = hiltViewModel(),
    onClickReadBookStart: (bookId: Long, slideId: Long) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    ReadStartScreenWithState(state, onClickReadBookStart)
}

@Composable
fun ReadStartScreenWithState(
    state: ReadStartViewModel.ReadStartUiState,
    onClickReadBookStart: (bookId: Long, slideId: Long) -> Unit = { _, _ -> }
) {
    when (state) {
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
    onClickReadBookStart: (bookId: Long, slideId: Long) -> Unit = { _, _ -> }
) {
    val openDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(modifier = Modifier.weight(weight = 1.0f, true)) {
            SelectiveImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                imageFile = state.coverImage
            )
            DividerRow()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = state.config.title)
                Row {
                    Text(text = "${state.config.version}")
                    Text(text = "${state.config.updateTime}")
                }

                Row {
                    Text(text = stringResource(id = R.string.book_config_user))
                    Text(text = state.config.user)
                }
                Row {
                    Text(text = stringResource(id = R.string.book_config_writer))
                    Text(text = state.config.writer)
                }
                Row {
                    Text(text = stringResource(id = R.string.book_config_illustrator))
                    Text(text = state.config.illustrator)
                }
                Text(text = state.config.description)
            }
        }
        DividerRow()
        Column {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(15),
                onClick = {
                    if (state.getSaveSlideId() != Const.ID_NOT_FOUND) {
                        openDialog.value = true
                    } else {
                        state.deleteBookPref()
                        onClickReadBookStart(state.config.bookId, Const.FIRST_SLIDE)
                    }
                }) {
                Text(text = stringResource(id = R.string.start_book))
            }
        }
    }
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


@Preview
@Composable
fun PreviewScreen() {
    ReadStartScreenLoaded(
        ReadStartViewModel.ReadStartUiState.Loaded(
            Config(
                bookId = 3000L,
                version = 1000L,
                updateTime = 3000000L,
                publishCode = "213545",
                email = "test@gmail.com",
                user = "유저",
                uid = "322436436",
                title = "타이틀",
                writer = "작가",
                illustrator = "작화가",
                description = "설명",
                coverImage = "커버",
            ), null, { 0 }, {})
    )
}