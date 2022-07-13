package com.raproject.whattowatch.ui.about_content

import androidx.lifecycle.viewModelScope
import com.raproject.whattowatch.models.ContentDetailsStatus
import com.raproject.whattowatch.repository.use_case.AddContentToFavoriteUseCase
import com.raproject.whattowatch.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class AboutContentViewModel : BaseViewModel() {

    private val contentMutableState = MutableStateFlow(ContentDetailsStatus.onLoading)

    private val addToFavoriteUseCase: AddContentToFavoriteUseCase by inject()

    private val isInFavoriteMutableStateFlow = MutableStateFlow(false)

    val isInFavoriteState: StateFlow<Boolean>
        get() = isInFavoriteMutableStateFlow

    val contentState: StateFlow<ContentDetailsStatus>
        get() = contentMutableState

    fun manageFavoriteStatus(onErrorAction:  (Throwable) -> Unit) = viewModelScope.launch(Dispatchers.Default) {
        contentMutableState.value.modelOrNull()?.let {
            if (isInFavoriteState.value.not()) addToFavorite(
                contentId = it.id,
                onError = onErrorAction
            )
            else removeFromFavorite(contentId = it.id, onError = onErrorAction)
        }
    }

    private suspend fun addToFavorite(contentId: String, onError: (Throwable) -> Unit) {
        addToFavoriteUseCase.invoke(value = contentId)
            .onSuccess { isInFavoriteMutableStateFlow.emit(true) }
            .onFailure(onError)
    }

    private suspend fun removeFromFavorite(contentId: String, onError: (Throwable) -> Unit) {
        contentMutableState.value.modelOrNull()?.let { }
    }


    private fun ContentDetailsStatus.modelOrNull(): ContentDetailsStatus.ContentInformationModel? {
        return if (this is ContentDetailsStatus.ContentInformationModel) this else null
    }
}