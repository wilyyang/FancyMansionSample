package com.cheesejuice.fancymansionsample.ui.contents.reader.start

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheesejuice.fancymansionsample.R
import com.cheesejuice.fancymansionsample.data.models.Config
import com.cheesejuice.fancymansionsample.data.repositories.PreferenceProvider
import com.cheesejuice.fancymansionsample.data.repositories.file.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReadStartViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferenceProvider: PreferenceProvider,
    private val fileRepository: FileRepository,
) : ViewModel(){
    private val _uiState = MutableStateFlow<ReadStartUiState>(ReadStartUiState.Empty)
    val uiState: StateFlow<ReadStartUiState> = _uiState

    fun initConfig(bookId: Long) {
        _uiState.value = ReadStartUiState.Loading(context.getString(R.string.loading_text_get_read_book))
        viewModelScope.launch(Dispatchers.IO) {
            makeBook()
            val config = fileRepository.getConfigFromFile(bookId)
            config?.also{
                _uiState.value = ReadStartUiState.Loaded(
                    config = it,
                    saveSlideId = preferenceProvider.getSaveSlideId(it.bookId),
                    coverImage = fileRepository.getImageFile(it.bookId, it.coverImage, isCover = true),
                    deleteBookPrefLambda = { bookId -> preferenceProvider.deleteBookPref(bookId) }
                )
            }?:also{
                _uiState.value = ReadStartUiState.Empty
            }
        }
    }

    private fun makeBook(){
        if(fileRepository.initRootFolder()){
            if(!preferenceProvider.isSampleMake()){
                fileRepository.createSampleBookFiles()
            }
        }
    }

    sealed class ReadStartUiState {
        object Empty : ReadStartUiState()
        class Loading(val message: String) : ReadStartUiState()
        class Loaded(
            val config: Config, val saveSlideId: Long, val coverImage: File?,
            private val deleteBookPrefLambda: (Long) -> Unit
        ) : ReadStartUiState() {
            fun deleteBookPref() = deleteBookPrefLambda(config.bookId)
        }
    }
}