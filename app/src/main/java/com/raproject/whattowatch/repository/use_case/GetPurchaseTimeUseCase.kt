package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.repository.app_settings.SettingRepository
import java.time.Instant

class GetPurchaseTimeUseCase(override val repository: SettingRepository) :
    SimpleOutCase<Instant, SettingRepository>() {

    override fun invoke(): Instant {
        return repository.getPurchaseTime()
    }
}