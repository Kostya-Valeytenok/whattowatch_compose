package com.raproject.whattowatch.ui.about_content

import androidx.lifecycle.viewModelScope
import com.raproject.whattowatch.models.ContentDetailsStatus
import com.raproject.whattowatch.models.ContentViewModel
import com.raproject.whattowatch.models.GetContentDetailsModel
import com.raproject.whattowatch.repository.use_case.AddContentToFavoriteUseCase
import com.raproject.whattowatch.repository.use_case.DeleteContentFromFavoriteUseCase
import com.raproject.whattowatch.repository.use_case.GetContentDetailsUseCase
import com.raproject.whattowatch.repository.use_case.GetIsInFavoriteStatusUseCase
import com.raproject.whattowatch.utils.BaseViewModel
import com.raproject.whattowatch.utils.Localization
import com.raproject.whattowatch.utils.Settings
import com.raproject.whattowatch.utils.convertToContentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.inject

class AboutContentViewModel() : BaseViewModel() {

    private val addToFavoriteUseCase: AddContentToFavoriteUseCase by inject()
    private val deleteFromFavoriteUseCase: DeleteContentFromFavoriteUseCase by inject()
    private val getIsInFavoriteStatusUseCase: GetIsInFavoriteStatusUseCase by inject()
    private val getContentDetailsUseCase: GetContentDetailsUseCase by inject()

    private var isInit = false

    private val isInFavoriteMutableStateFlow = MutableStateFlow(false)
    val isInFavoriteState: StateFlow<Boolean>
        get() = isInFavoriteMutableStateFlow

    private val contentLoadingMutableState =
        MutableStateFlow<ContentDetailsStatus>(ContentDetailsStatus.onLoading)
    val contentLoadingState: StateFlow<ContentDetailsStatus>
        get() = contentLoadingMutableState

    private val contentMutableState = MutableStateFlow(ContentViewModel())
    val contentState: StateFlow<ContentViewModel>
        get() = contentMutableState


    private lateinit var contentId: String

    fun init(contentId: String) {
        this.contentId = contentId
        if (isInit.not()) {
            isInit = true
            println("Init")
            viewModelScope.launch(Dispatchers.Default) { getIsInFavoriteStatus() }
            viewModelScope.launch(Dispatchers.Default) { manageContentByLocalizationState() }
        }
    }

    private suspend fun getIsInFavoriteStatus(contentId: String = this.contentId) {
        getIsInFavoriteStatusUseCase.invoke(value = contentId).await()
            .onSuccess { isInFavoriteMutableStateFlow.emit(it) }
            .onFailure { isInFavoriteMutableStateFlow.emit(false) }
    }

    private suspend fun manageContentByLocalizationState(contentId: String = this.contentId) {
        Settings.localization.collect { localization -> getContentDetails(contentId, localization) }
    }

    private suspend fun getContentDetails(contentId: String, localization: Localization) =
        withContext(Dispatchers.Default) {
            contentLoadingMutableState.emit(ContentDetailsStatus.onLoading)
            val result = getContentDetailsUseCase.invoke(
                GetContentDetailsModel(
                    contentId,
                    localization
                )
            ).await()
            contentLoadingMutableState.emit(result)
            val currentContentState = result
            if (currentContentState is ContentDetailsStatus.onLoaded) {
                contentMutableState.emit(currentContentState.convertToContentViewModel())
            } else {
                val errorState = currentContentState as ContentDetailsStatus.OnFailed
                println(errorState.throwable)
            }

        }

    fun manageFavoriteStatus(onErrorAction: (Throwable) -> Unit) =
        viewModelScope.launch(Dispatchers.Default) {
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
            .onSuccess { isInFavoriteMutableStateFlow.emit(false) }
            .onFailure(onError)
    }


    private fun ContentDetailsStatus.modelOrNull(): ContentDetailsStatus.onLoaded? {
        return if (this is ContentDetailsStatus.onLoaded) this else null
    }
}