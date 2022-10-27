package com.cheesejuice.fancymansionsample.ui.contents.reader.slide

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheesejuice.fancymansionsample.Const
import com.cheesejuice.fancymansionsample.data.models.ChoiceItem
import com.cheesejuice.fancymansionsample.data.models.Logic
import com.cheesejuice.fancymansionsample.data.models.Slide
import com.cheesejuice.fancymansionsample.data.models.SlideLogic
import com.cheesejuice.fancymansionsample.data.repositories.PreferenceProvider
import com.cheesejuice.fancymansionsample.data.repositories.file.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReadSlideViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val fileRepository: FileRepository,
) : ViewModel(){

    // loading
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    // logic
    private lateinit var _logic: Logic

    // slide
    private var _slide: Slide? = null
    val slide: Slide?
        get() = _slide

    private var _slideLogic: SlideLogic? = null
    val slideLogic: SlideLogic?
        get() = _slideLogic

    private var _passChoiceItems: ArrayList<ChoiceItem> = arrayListOf()
    val passChoiceItems: ArrayList<ChoiceItem>
        get() = _passChoiceItems

    val coverImage: File?
        get() = fileRepository.getImageFile(_logic.bookId, _slide!!.slideImage)

    fun initLogicSlide(bookId: Long, slideId: Long, publishCode: String) {
        _loading.value = true

        _logic = fileRepository.getLogicFromFile(bookId)!!

        makeSlideFromFile(if(slideId == Const.FIRST_SLIDE && _logic.logics.size > 0) _logic.logics[0].slideId else slideId)

        /** [#SAVE] Check Save */
        val savedSlideId = preferenceProvider.getSaveSlideId(_logic.bookId)
        val firstPlay = _slide!!.slideId != savedSlideId

        if (firstPlay) {
            /** [#ID_COUNT] Slide Id (if first play slide) */
            preferenceProvider.incrementIdCount(_logic.bookId, _slide!!.slideId)
        }

        _loading.value = false
    }

    fun moveToNextSlide(choiceItem: ChoiceItem) {
        _loading.value = true

        /** [#ID_COUNT] Choice Item */
        preferenceProvider.incrementIdCount(_logic.bookId, choiceItem.id)

        var nextSlideId = Const.END_SLIDE_ID
        for(enterItem in choiceItem.enterItems) {
            if(preferenceProvider.checkConditions(_logic.bookId, enterItem.enterConditions)){
                /** [#ID_COUNT] Enter Item */
                preferenceProvider.incrementIdCount(_logic.bookId, enterItem.id)
                nextSlideId = enterItem.enterSlideId
                break
            }
        }

        makeSlideFromFile(nextSlideId)

        _slide?.let {
            /** [#ID_COUNT] Slide Id */
            preferenceProvider.incrementIdCount(_logic.bookId, it.slideId)

            /** [#SAVE] Save Slide */
            preferenceProvider.setSaveSlideId(_logic.bookId, it.slideId)
        }

        viewModelScope.launch {
            delay(500L)
            _loading.value = false
        }
    }

    private fun makeSlideFromFile(slideId:Long) {
        _slide = fileRepository.getSlideFromFile(_logic.bookId, slideId)
        _slideLogic = _logic.logics.find { it.slideId == _slide?.slideId }

        _passChoiceItems = arrayListOf()
        for(choiceItem in _slideLogic!!.choiceItems){
            if(preferenceProvider.checkConditions(_logic.bookId, choiceItem.showConditions)){
                _passChoiceItems.add(choiceItem)
            }
        }
    }
}