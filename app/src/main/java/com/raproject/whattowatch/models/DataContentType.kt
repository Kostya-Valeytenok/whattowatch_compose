package com.raproject.whattowatch.models

sealed class DataContentType() {

    object POSTER : DataContentType()
    object TITLE : DataContentType()
    object GENRES : DataContentType()
    object YEARANDDURATION : DataContentType()
    object YEAR : DataContentType()
    object COUNTRY : DataContentType()
    object DURATION : DataContentType()
    data class SPACE(val id: String) : DataContentType()
    object CAST : DataContentType()
    object DEVRATING : DataContentType()
    object KINOPOISKRATING : DataContentType()
    object DIRECTOR : DataContentType()
    object DESCRIPTION : DataContentType()

}