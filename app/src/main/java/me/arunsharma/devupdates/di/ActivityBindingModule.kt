package me.arunsharma.devupdates.di

import dagger.Module
import me.arunsharma.devupdates.di.activity.MainComponent

@Module(subcomponents = [MainComponent::class])
class ActivityBindingModule {
}