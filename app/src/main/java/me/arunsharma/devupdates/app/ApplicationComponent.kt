package me.arunsharma.devupdates.app

import android.app.Application
import com.dev.network.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import me.arunsharma.devupdates.di.ActivityBindingModule
import me.arunsharma.devupdates.di.ApplicationModule
import me.arunsharma.devupdates.di.DebugApplicationModule
import me.arunsharma.devupdates.di.activity.MainComponent
import me.arunsharma.devupdates.di.data.DataModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ActivityBindingModule::class,
        DebugApplicationModule::class,
        NetworkModule::class,
        DataModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: DevUpdatesApp)

    fun mainComponent(): MainComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(application: Application): Builder
        fun build(): ApplicationComponent
    }
}
