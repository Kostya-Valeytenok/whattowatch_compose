package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.repository.app_settings.SettingRepository
import com.raproject.whattowatch.utils.Localization

class GetLocalizationUseCase(override val repository: SettingRepository) :
    SimpleOutCase<Localization, SettingRepository>() {

    override fun invoke(): Localization {
        return repository.getLocalization()
    }
}