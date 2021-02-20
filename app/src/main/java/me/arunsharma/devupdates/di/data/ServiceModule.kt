package me.arunsharma.devupdates.di.data

import com.dev.services.models.ServiceRequest
import com.devupdates.github.di.GithubModule
import com.dev.services.repo.ServiceIntegration

import com.devupdates.medium.di.MediumModule
import dagger.Module
import dagger.multibindings.Multibinds

@Module(includes = [GithubModule::class, MediumModule::class])
abstract class ServiceModule {
    @Multibinds
    abstract fun services(): Map<String, ServiceIntegration>
}