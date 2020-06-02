package me.arunsharma.devupdates.di.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.core.di.annotations.ViewModelKey
import com.dev.core.utils.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.arunsharma.devupdates.ui.viewmodels.VMFeed

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: AppViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(VMFeed::class)
    internal abstract fun vmFeed(viewModel: VMFeed): ViewModel
}