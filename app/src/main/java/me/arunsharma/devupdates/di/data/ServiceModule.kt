package me.arunsharma.devupdates.di.data

import com.dev.services.github.di.GithubModule
import com.dev.services.repo.ServiceIntegration
import com.dev.services.twitter.di.TwitterModule
import dagger.Module
import dagger.multibindings.Multibinds

@Module(includes = [GithubModule::class, TwitterModule::class])
abstract class ServiceModule {
    @Multibinds
    abstract fun services(): Map<String, ServiceIntegration>
}