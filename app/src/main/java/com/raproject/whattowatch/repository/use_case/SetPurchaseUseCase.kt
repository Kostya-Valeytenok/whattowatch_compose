package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.repository.app_settings.SettingRepository
import java.time.Instant

class SetPurchaseUseCase(override val repository: SettingRepository) :
    InOutCase<Instant, Result<Unit>, SettingRepository>() {

    override suspend fun invoke(value: Instant): Result<Unit> =
        runCatching { repository.setPurchaseTime(value) }

}