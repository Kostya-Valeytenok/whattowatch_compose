package com.raproject.whattowatch.ui.about_content

import androidx.lifecycle.ViewModel
import com.raproject.whattowatch.models.ContentDetailsStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AboutContentViewModel : ViewModel() {

    private val contentMutableState = MutableStateFlow(ContentDetailsStatus.onLoading)

    val contentState : StateFlow<ContentDetailsStatus>
        get() = contentMutableState

    fun addToFavorite(){
        contentMutableState.value.modelOrNull()?.let {  }
    }

    fun RemoveFromFavorite(){
        contentMutableState.value.modelOrNull()?.let {  }
    }


    private fun ContentDetailsStatus.modelOrNull() : ContentDetailsStatus.ContentInformationModel?{
       return if(this is ContentDetailsStatus.ContentInformationModel)  this else null
    }
}