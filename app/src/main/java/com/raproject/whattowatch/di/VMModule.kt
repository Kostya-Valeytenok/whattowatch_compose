package com.raproject.whattowatch.di

import com.raproject.whattowatch.ui.about_content.AboutContentViewModel
import com.raproject.whattowatch.ui.content_list.AppActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

var vmModule = module {
    viewModel { AppActivityViewModel() }
    viewModel { AboutContentViewModel() }
}

