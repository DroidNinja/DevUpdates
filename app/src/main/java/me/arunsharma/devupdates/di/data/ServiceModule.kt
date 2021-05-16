package me.arunsharma.devupdates.di.data

import com.dev.rss.di.RSSModule
import com.dev.androidweekly.di.AndroidWeeklyModule
import com.dev.services.repo.ServiceIntegration
import com.devupdates.github.di.GithubModule

import com.devupdates.medium.di.MediumModule
import dagger.Module
import dagger.multibindings.Multibinds

@Module(includes = [AndroidWeeklyModule::class, MediumModule::class, RSSModule::class, GithubModule::class])
abstract class ServiceModule {
    @Multibinds
    abstract fun services(): Map<String, ServiceIntegration>
}