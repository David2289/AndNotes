package com.example.photos.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photos.business.datasource.local.androom.entity.PictureEntity

class PhotosViewModel: ViewModel() {

    var userListLiveData: MutableLiveData<List<PictureEntity>> = MutableLiveData()
    var photoList = ArrayList<PictureEntity>()

    fun getPictures() {

    }

}