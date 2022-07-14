package com.raproject.whattowatch.ui.about_content

import androidx.lifecycle.viewModelScope
import com.raproject.whattowatch.models.ContentDetailsStatus
import com.raproject.whattowatch.repository.use_case.AddContentToFavoriteUseCase
import com.raproject.whattowatch.repository.use_case.DeleteContentFromFavoriteUseCase
import com.raproject.whattowatch.repository.use_case.GetIsInFavoriteStatusUseCase
import com.raproject.whattowatch.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class AboutContentViewModel() : BaseViewModel() {

    private val addToFavoriteUseCase: AddContentToFavoriteUseCase by inject()
    private val deleteFromFavoriteUseCase: DeleteContentFromFavoriteUseCase by inject()
    private val getIsInFavoriteStatusUseCase: GetIsInFavoriteStatusUseCase by inject()
    private var isInit = false

    private val isInFavoriteMutableStateFlow = MutableStateFlow(false)

    val isInFavoriteState: StateFlow<Boolean>
        get() = isInFavoriteMutableStateFlow

    var contentId:String =""

    fun init(contentId: String){
        this.contentId = contentId
        if(isInit.not()) {
            isInit = true
            println("Init")
            viewModelScope.launch(Dispatchers.Default) {
                getIsInFavoriteStatusUseCase.invoke(value = contentId).await()
                    .onSuccess {
                        println("Success, Status: $it")
                        isInFavoriteMutableStateFlow.emit(it)
                    }.onFailure { isInFavoriteMutableStateFlow.emit(false) }
            }
        }
    }


    private val contentMutableState = MutableStateFlow(ContentDetailsStatus.onLoading)


    val contentState: StateFlow<ContentDetailsStatus>
        get() = contentMutableState

    fun manageFavoriteStatus(onErrorAction:  (Throwable) -> Unit) = viewModelScope.launch(Dispatchers.Default) {
        if (isInFavoriteState.value.not()) addToFavorite(
            contentId = contentId,
            onError = onErrorAction
        )
        else removeFromFavorite(contentId = contentId, onError = onErrorAction)
    }

    private suspend fun addToFavorite(contentId: String, onError: (Throwable) -> Unit) {
        addToFavoriteUseCase.invoke(value = contentId)
            .onSuccess { isInFavoriteMutableStateFlow.emit(true) }
            .onFailure(onError)
    }

    private suspend fun removeFromFavorite(contentId: String, onError: (Throwable) -> Unit) {
        deleteFromFavoriteUseCase.invoke(value = contentId)
            .onSuccess {  isInFavoriteMutableStateFlow.emit(false) }
            .onFailure(onError)
    }


    private fun ContentDetailsStatus.modelOrNull(): ContentDetailsStatus.ContentInformationModel? {
        return if (this is ContentDetailsStatus.ContentInformationModel) this else null
    }
}