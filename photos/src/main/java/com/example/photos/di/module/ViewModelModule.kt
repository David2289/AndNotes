package com.example.photos.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.brastlewark.ui.utility.factory.ViewModelFactory
import com.example.photos.di.key.ViewModelKey
import com.example.photos.ui.viewmodel.PhotosViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [RepositoryModule::class])
abstract class ViewModelModule {

    @Binds
    abstract fun binViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PhotosViewModel::class)
    abstract fun bindListViewModel(photosViewModel: PhotosViewModel): ViewModel

}