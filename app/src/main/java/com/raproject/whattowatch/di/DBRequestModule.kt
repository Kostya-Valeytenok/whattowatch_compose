package com.raproject.whattowatch.di

import android.os.Bundle
import com.raproject.whattowatch.repository.request.*
import org.koin.dsl.module

val dbRequestsModule = module {
    factory { (params: Bundle) -> GetGenresById(params = params) }
    factory { (params: Bundle) -> GetSmallPosterById(params =params) }
    factory { (params: Bundle) -> GetTop100(params =params) }
    factory { (params: Bundle) -> GetContentCardsByType(params =params) }
    factory { (id:String) -> PostContentIntoFavorite(contentId = id) }
    factory { (params:Bundle)-> GetIsInFavoriteStatus(params = params) }
}
