package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.repository.app_settings.SettingRepository
import com.raproject.whattowatch.utils.Localization

class SetLocalizationUseCase(override val repository: SettingRepository) :
    InOutCase<Localization, Result<Unit>, SettingRepository>() {

    override suspend fun invoke(value: Localization): Result<Unit> =
        runCatching { repository.setLocalization(value) }

}