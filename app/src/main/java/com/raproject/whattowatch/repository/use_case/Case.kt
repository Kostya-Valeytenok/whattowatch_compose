package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.repository.Repository
import org.koin.core.component.KoinComponent

abstract class Case<T:Repository> : KoinComponent {

    protected abstract val repository:T
}