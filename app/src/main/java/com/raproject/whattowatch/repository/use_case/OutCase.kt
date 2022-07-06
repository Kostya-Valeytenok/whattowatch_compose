package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.repository.Repository

abstract class OutCase<out O, R:Repository> : Case<R>() {

    abstract suspend fun invoke(): O
}
