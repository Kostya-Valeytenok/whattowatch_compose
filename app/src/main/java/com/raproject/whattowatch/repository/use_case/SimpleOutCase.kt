package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.repository.Repository

abstract class SimpleOutCase<out O, R : Repository> : Case<R>() {

    abstract fun invoke(): O
}
