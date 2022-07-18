package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.repository.app_settings.SettingRepository
import com.raproject.whattowatch.utils.TableRow

class GetOrderRowUseCase(override val repository: SettingRepository) :
    SimpleOutCase<TableRow, SettingRepository>() {

    override fun invoke(): TableRow {
        return repository.getOrderRow()
    }
}