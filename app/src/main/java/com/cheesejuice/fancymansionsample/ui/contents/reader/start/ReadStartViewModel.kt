package com.cheesejuice.fancymansionsample.ui.contents.reader.start

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheesejuice.fancymansionsample.Const
import com.cheesejuice.fancymansionsample.R
import com.cheesejuice.fancymansionsample.data.SampleBook
import com.cheesejuice.fancymansionsample.data.models.Config
import com.cheesejuice.fancymansionsample.data.models.Logic
import com.cheesejuice.fancymansionsample.data.models.Slide
import com.cheesejuice.fancymansionsample.data.repositories.PreferenceProvider
import com.cheesejuice.fancymansionsample.data.repositories.file.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

data class ReadStartUiModel(
    val saveSlideId: Long,
    val config: Config,
    val coverImage: File
)

@HiltViewModel
class ReadStartViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferenceProvider: PreferenceProvider,
    private val fileRepository: FileRepository,
) : ViewModel(){

    sealed class ReadStartUiState {
        object Empty : ReadStartUiState()
        object Loading : ReadStartUiState()
        class Loaded(val data: ReadStartUiModel) : ReadStartUiState()
    }

    private val _uiState = MutableStateFlow<ReadStartUiState>(ReadStartUiState.Empty)
    val uiState: StateFlow<ReadStartUiState> = _uiState

//    init {
//        _loading.value = true
//        _init.value = false
//    }

    private fun fetchWeather() {
        _uiState.value = ReadStartUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                makeBook()
                _uiState.value = ReadStartUiState.Loaded(
                    ReadStartUiModel()
                )
            } catch (ex: Exception) {
            }
        }
    }

    fun initConfig(bookId: Long) {
        setLoading(true, context.getString(R.string.loading_text_get_read_book))
        makeBook()

        _config = fileRepository.getConfigFromFile(bookId)
        setLoading(false)
        _init.value = true
    }

    private fun setLoading(loading: Boolean, loadingText: String = "") {
        _loadingText = loadingText
        _loading.value = loading
    }

    fun deleteBookPref() =
        preferenceProvider.deleteBookPref(_config!!.bookId)

    private fun makeBook(){
        if(fileRepository.initRootFolder()){
            if(!preferenceProvider.isSampleMake()){
                fileRepository.createSampleBookFiles()
            }
        }
    }
}