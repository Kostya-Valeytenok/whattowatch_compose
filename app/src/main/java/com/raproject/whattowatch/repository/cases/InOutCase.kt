package com.raproject.whattowatch.repository.cases

import com.raproject.whattowatch.repository.Repository

abstract class InOutCase<in I, out O, R:Repository> : Case<R>() {

    abstract suspend fun invoke(value: I): O
}
