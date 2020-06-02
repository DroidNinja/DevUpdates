package me.arunsharma.devupdates.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import me.arunsharma.devupdates.di.ActivityBindingModule
import me.arunsharma.devupdates.di.ApplicationModule
import me.arunsharma.devupdates.di.DebugApplicationModule
import me.arunsharma.devupdates.di.activity.MainComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ActivityBindingModule::class,
        DebugApplicationModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: DevUpdatesApp)

    fun mainComponent(): MainComponent.Factory

    @Component.Factory
    interface Builder {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}
