package com.raproject.whattowatch.repository.use_case

import com.raproject.whattowatch.repository.app_settings.SettingRepository
import com.raproject.whattowatch.utils.OrderType

class GetOrderTypeUseCase(override val repository: SettingRepository) :
    SimpleOutCase<OrderType, SettingRepository>() {

    override fun invoke(): OrderType {
        return repository.getOrderType()
    }
}