package me.arunsharma.devupdates.di.activity

import com.dev.core.di.annotations.ActivityScope
import dagger.Subcomponent
import me.arunsharma.devupdates.di.viewmodels.ViewModelModule
import me.arunsharma.devupdates.ui.MainActivity
import me.arunsharma.devupdates.ui.fragments.FeedFragment

@ActivityScope
@Subcomponent(modules = [ViewModelModule::class])
interface MainComponent {

    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(feedFragment: FeedFragment)
}