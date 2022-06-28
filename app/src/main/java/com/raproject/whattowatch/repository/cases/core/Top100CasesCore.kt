package com.raproject.whattowatch.repository.cases.core

import com.raproject.whattowatch.repository.cases.core.base.BaseContentCaseCore
import com.raproject.whattowatch.utils.ContentType
import org.koin.core.component.KoinComponent

class Top100CasesCore : KoinComponent, BaseContentCaseCore() {
    override val contentType: ContentType get() = ContentType.Top100
}
