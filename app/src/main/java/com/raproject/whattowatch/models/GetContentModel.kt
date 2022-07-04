package com.raproject.whattowatch.models

import com.raproject.whattowatch.utils.Localization

data class GetContentModel(val localization: Localization, val orderCommand:String = "")
