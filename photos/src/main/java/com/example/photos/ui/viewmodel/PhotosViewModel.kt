package com.example.photos.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photos.business.datasource.local.androom.entity.PictureEntity
import com.example.photos.business.repository.PhotosRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class PhotosViewModel @Inject constructor(private val photosRepository: PhotosRepository): ViewModel() {

    var pictureListLiveData: MutableLiveData<List<PictureEntity>> = MutableLiveData()
    var pictureList = ArrayList<PictureEntity>()

    fun getPictures() {
        photosRepository.getPictureList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(pictureList: List<PictureEntity>) {
        this.pictureList.addAll(pictureList)
        pictureListLiveData.value = this.pictureList
    }

    private fun handleError(t: Throwable) {
        Log.w("RETROFIT", "HAS BEEN AN ERROR: " + t.message)
    }

    fun savePicture(pictureEntity: PictureEntity) {
        photosRepository.savePicture(pictureEntity)
    }

    fun deletePicture(pictureEntity: PictureEntity) {
        photosRepository.deletePicture(pictureEntity)
    }

}