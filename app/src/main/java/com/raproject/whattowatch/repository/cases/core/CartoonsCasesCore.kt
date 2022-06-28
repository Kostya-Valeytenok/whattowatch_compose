package com.raproject.whattowatch.repository.cases.core

import com.raproject.whattowatch.repository.cases.core.base.BaseContentCaseCore
import com.raproject.whattowatch.utils.ContentType
import org.koin.core.component.KoinComponent

class CartoonsCasesCore : KoinComponent, BaseContentCaseCore() {
    override val contentType: ContentType get() = ContentType.Cartoons
}
