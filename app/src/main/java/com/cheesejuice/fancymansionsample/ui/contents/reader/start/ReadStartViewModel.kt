package com.cheesejuice.fancymansionsample.ui.contents.reader.start

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class ReadStartViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferenceProvider: PreferenceProvider,
    private val fileRepository: FileRepository,
) : ViewModel(){

    // loading
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private var _loadingText: String = ""
    val loadingText: String
        get() = _loadingText

    // init
    private val _init = MutableLiveData<Boolean>()
    val init: LiveData<Boolean>
        get() = _init

    private var _config: Config? = null
    val config: Config?
        get() = _config

    val coverImage: File?
        get() = fileRepository.getImageFile(
            _config!!.bookId,
            _config!!.coverImage,
            isCover = true
        )

    // get save slide id
    val saveSlideId: Long
        get() = preferenceProvider.getSaveSlideId(_config!!.bookId)

    init {
        _loading.value = true
        _init.value = false
    }

    fun makeBook(){
        if(fileRepository.initRootFolder()){
            if(!preferenceProvider.isSampleMake()){
                fileRepository.createSampleBookFiles()
            }
        }
    }

    fun initConfig(bookId: Long, publishCode: String) {
        setLoading(true, context.getString(R.string.loading_text_get_read_book))
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
}