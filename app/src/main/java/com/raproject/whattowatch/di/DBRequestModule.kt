package com.raproject.whattowatch.di

import android.os.Bundle
import com.raproject.whattowatch.repository.request.GetContentCardsByType
import com.raproject.whattowatch.repository.request.GetGenresById
import com.raproject.whattowatch.repository.request.GetSmallPosterById
import com.raproject.whattowatch.repository.request.GetTop100ByDevRating
import org.koin.dsl.module

val dbRequestsModule = module {
    factory { (params: Bundle) -> GetGenresById(params = params) }
    factory { (params: Bundle) -> GetSmallPosterById(params =params) }
    factory { (params: Bundle) -> GetTop100ByDevRating(params =params) }
    factory { (params: Bundle) -> GetContentCardsByType(params =params) }
}
