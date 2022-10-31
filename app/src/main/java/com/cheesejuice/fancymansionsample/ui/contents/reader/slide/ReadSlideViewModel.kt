package com.cheesejuice.fancymansionsample.ui.contents.reader.slide

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheesejuice.fancymansionsample.Const
import com.cheesejuice.fancymansionsample.data.models.*
import com.cheesejuice.fancymansionsample.data.repositories.PreferenceProvider
import com.cheesejuice.fancymansionsample.data.repositories.file.FileRepository
import com.cheesejuice.fancymansionsample.nav.ReadSlide
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReadSlideViewModel @Inject constructor(
    private val preferenceProvider: PreferenceProvider,
    private val fileRepository: FileRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _uiState = MutableStateFlow<ReadSlideUiState>(ReadSlideUiState.Empty)
    val uiState: StateFlow<ReadSlideUiState> = _uiState

    // logic
    private lateinit var _logic: Logic

    init {
        val bookId = savedStateHandle.get<Long>(ReadSlide.readBookIdArg)!!
        val slideId = savedStateHandle.get<Long>(ReadSlide.readSlideIdArg)!!

        _uiState.value = ReadSlideUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _logic = fileRepository.getLogicFromFile(bookId)!!

            val state = makeSlideFromFile(if(slideId == Const.FIRST_SLIDE && _logic.logics.size > 0) _logic.logics[0].slideId else slideId)

            if(state is ReadSlideUiState.Loaded){
                /** [#SAVE] Check Save */
                val savedSlideId = preferenceProvider.getSaveSlideId(_logic.bookId)
                val firstPlay = state.slide.slideId != savedSlideId

                if (firstPlay) {
                    /** [#ID_COUNT] Slide Id (if first play slide) */
                    preferenceProvider.incrementIdCount(_logic.bookId, state.slide.slideId)
                }
            }
            _uiState.value = state
        }
    }

    private fun moveToNextSlide(choiceItem: ChoiceItem) {
        _uiState.value = ReadSlideUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
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

            val state = makeSlideFromFile(nextSlideId)
            if(state is ReadSlideUiState.Loaded){
                /** [#ID_COUNT] Slide Id */
                preferenceProvider.incrementIdCount(_logic.bookId, nextSlideId)
                /** [#SAVE] Save Slide */
                preferenceProvider.setSaveSlideId(_logic.bookId, nextSlideId)
            }
            _uiState.value = state
        }
    }

    private fun makeSlideFromFile(slideId:Long): ReadSlideUiState {
        fileRepository.getSlideFromFile(_logic.bookId, slideId)?.let { slide ->
            _logic.logics.find { it.slideId == slide.slideId }?.let { slideLogic ->
                val passChoiceItems: ArrayList<ChoiceItem> = arrayListOf()
                for(choiceItem in slideLogic.choiceItems){
                    if(preferenceProvider.checkConditions(_logic.bookId, choiceItem.showConditions)){
                        passChoiceItems.add(choiceItem)
                    }
                }
                return ReadSlideUiState.Loaded(
                    slide = slide,
                    passChoiceItems = passChoiceItems,
                    slideImage = fileRepository.getImageFile(_logic.bookId, slide.slideImage),
                    moveToNextSlideLambda = { choiceItem -> moveToNextSlide(choiceItem) })
            }
        }
        return ReadSlideUiState.Empty
    }

    sealed class ReadSlideUiState {
        object Empty : ReadSlideUiState()
        object Loading : ReadSlideUiState()
        class Loaded(
            val slide: Slide, val passChoiceItems: ArrayList<ChoiceItem>, val slideImage: File?,
            private val moveToNextSlideLambda: (choiceItem: ChoiceItem) -> Unit
        ) : ReadSlideUiState() {
            fun moveToNextSlide(choiceItem: ChoiceItem) = moveToNextSlideLambda(choiceItem)
        }
    }
}