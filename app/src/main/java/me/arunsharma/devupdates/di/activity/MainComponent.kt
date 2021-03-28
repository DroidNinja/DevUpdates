package me.arunsharma.devupdates.di.activity

import com.dev.core.di.annotations.ActivityScope
import com.dev.core.di.annotations.DispatcherModule
import dagger.Subcomponent
import me.arunsharma.devupdates.di.ApplicationModule
import me.arunsharma.devupdates.di.data.ServiceModule
import me.arunsharma.devupdates.di.viewmodels.ViewModelModule
import me.arunsharma.devupdates.ui.MainActivity
import me.arunsharma.devupdates.ui.fragments.BookmarksFragment
import me.arunsharma.devupdates.ui.fragments.addsource.AddDataSourceFragment
import me.arunsharma.devupdates.ui.fragments.feed.FeedFragment
import me.arunsharma.devupdates.ui.fragments.feed.FeedListFragment

@ActivityScope
@Subcomponent(modules = [
    DispatcherModule::class,
    ViewModelModule::class,
    ServiceModule::class])
interface MainComponent {

    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(feedListFragment: FeedListFragment)
    fun inject(feedFragment: FeedFragment)
    fun inject(fragment: BookmarksFragment)
    fun inject(feedFragment: AddDataSourceFragment)
}