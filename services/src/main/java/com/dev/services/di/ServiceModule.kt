package com.dev.services.di

import com.dev.androidweekly.di.AndroidWeeklyModule
import com.dev.kotlinweekly.di.KotlinWeeklyModule
import com.dev.rss.di.RSSModule
import com.dev.services.api.repo.ServiceIntegration
import com.devupdates.github.di.GithubModule
import com.devupdates.medium.di.MediumModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.Multibinds

@InstallIn(SingletonComponent::class)
@Module(includes = [AndroidWeeklyModule::class, MediumModule::class, RSSModule::class, GithubModule::class, KotlinWeeklyModule::class])
abstract class ServiceModule {
    @Multibinds
    abstract fun services(): Map<String, ServiceIntegration>
}