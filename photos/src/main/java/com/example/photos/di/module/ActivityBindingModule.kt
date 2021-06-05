package com.example.photos.di.module

import com.example.photos.ui.activity.PhotosActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun bindPhotosActivity(): PhotosActivity

}