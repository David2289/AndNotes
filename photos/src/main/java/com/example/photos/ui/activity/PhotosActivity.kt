package com.example.photos.ui.activity

import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.*
import com.example.commons.ui.component.enums.DialogType
import com.example.commons.ui.component.extensions.DialogExtensions.Companion.setup
import com.example.commons.ui.model.button.ButtonModel
import com.example.photos.R
import com.example.photos.business.datasource.local.androom.entity.PictureEntity
import com.example.photos.databinding.PhotosActivityBinding
import com.example.photos.ui.adapter.PhotosAdapter
import com.example.photos.ui.viewmodel.PhotosViewModel
import com.example.photos.utility.manager.PermissionManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class PhotosActivity: DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: PhotosViewModel
    lateinit var binding: PhotosActivityBinding
    lateinit var cameraPermLauncher: ActivityResultLauncher<String>
    lateinit var galleryPermLauncher: ActivityResultLauncher<String>
    lateinit var takePictureLauncher: ActivityResultLauncher<Uri>
    lateinit var setScreenLauncher: ActivityResultLauncher<Intent>
    lateinit var mediaDialog: Dialog
    lateinit var setDialog: Dialog
    lateinit var removeDialog: Dialog
    var adapter: PhotosAdapter? = null
    var photoList = ArrayList<PictureEntity>()
    var imageUri: Uri? = null
    private var currentPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(PhotosViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.photos_activity)
        binding.viewModel = viewModel

        val observer = Observer<List<PictureEntity>> { result -> configUI(result) }
        viewModel.pictureListLiveData.observe(this, observer)

        viewModel.getPictures()

        cameraPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            if (isGranted) {
                openCamera()
            }
        }
        galleryPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            if (isGranted) {

            }
        }
        takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { taken ->
            if (taken) {
                val pictureEntity = PictureEntity(imageUri.toString())
                viewModel.savePicture(pictureEntity)
                viewModel.getPictures()
            }
        }

        setScreenLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            print("Settings results")
        }

        setContentView(binding.root)
    }


    private fun configUI(pictureList: List<PictureEntity>) {
        if (pictureList.isEmpty()) {
            configEmptyUI()
        } else {
            configPhotosUI(pictureList)
        }
    }


    private fun configEmptyUI() {
        binding.photosContent.visibility = View.GONE
        binding.emptyContent.visibility = View.VISIBLE
        binding.emptyContent.setOnClickListener { openMediaChooser() }
    }


    private fun configPhotosUI(pictureList: List<PictureEntity>) {
        binding.photosContent.visibility = View.VISIBLE
        binding.emptyContent.visibility = View.GONE
        if (adapter == null) {
            photoList.addAll(pictureList)
            adapter = PhotosAdapter(photoList)
            binding.viewpager.adapter = adapter
            binding.dotsIndicator.setViewPager2(binding.viewpager)
            binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPosition = position
                }
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when(state) {
                        SCROLL_STATE_IDLE -> {
                            binding.addPhoto.isEnabled = true
                            binding.removePhoto.isEnabled = true
                        }
                        SCROLL_STATE_DRAGGING -> {
                            binding.addPhoto.isEnabled = false
                            binding.removePhoto.isEnabled = false
                        }
                        SCROLL_STATE_SETTLING -> {}
                    }
                }
            })
        }
        else {
            photoList.clear()
            photoList.addAll(pictureList)
            adapter?.notifyDataSetChanged()
        }
        binding.addPhoto.setOnClickListener { openMediaChooser() }
        binding.removePhoto.setOnClickListener { openRemoveDialog() }
    }


    private fun openMediaChooser() {
        val btnMdl1 = ButtonModel(
            title = R.string.dialog_media_chooser_btn_camera,
            endIcon = R.drawable.ic_camera_black,
            onClick = {
                validateCameraPerm()
                mediaDialog.dismiss()
            })

        val btnMdl2 = ButtonModel(
            title = R.string.dialog_media_chooser_btn_gallery,
            endIcon = R.drawable.ic_folder_black,
            onClick = {
                validateGalleryPerm()
                mediaDialog.dismiss()
            })

        mediaDialog = Dialog(this).setup(
            type = DialogType.EXPANDED_BUTTONS,
            title = R.string.dialog_media_chooser_title,
            desc = R.string.dialog_media_chooser_desc,
            buttonMdl1 = btnMdl1,
            buttonMdl2 = btnMdl2
        )
        mediaDialog.show()
    }


    private fun openRemoveDialog() {
        val btnMdl1 = ButtonModel(
            title = R.string.commons_no,
            onClick = { removeDialog.dismiss() }
        )
        val btnMdl2 = ButtonModel(
            title = R.string.commons_yes,
            onClick = {
                viewModel.deletePicture(photoList[currentPosition])
                viewModel.getPictures()
                removeDialog.dismiss()
            }
        )
        removeDialog = Dialog(this).setup(
            type = DialogType.WRAPPED_BUTTONS,
            title = R.string.dialog_remove_photo_title,
            desc = R.string.dialog_remove_photo_desc,
            buttonMdl1 = btnMdl1,
            buttonMdl2 = btnMdl2
        )
        removeDialog.show()
    }


    private fun validateCameraPerm() {
        val permission = Manifest.permission.CAMERA
        PermissionManager.validate(
                activity = this,
                permission = permission,
                onPermNotGranted = { cameraPermLauncher.launch(permission) },
                onPermGranted = { openCamera() },
                onPermReqDisabled = { showCameraSettingDialog() }
        )
    }


    private fun showCameraSettingDialog() {
        val btnMdl1 = ButtonModel(
            title = R.string.dialog_set_camera_btn_title,
            onClick = { toSettings() })
        setDialog = Dialog(this).setup(
                type = DialogType.WRAPPED_BUTTONS,
                title = R.string.dialog_set_camera_title,
                desc = R.string.dialog_set_camera_desc,
                buttonMdl1 = btnMdl1
        )
        setDialog.show()
    }


    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        takePictureLauncher.launch(imageUri)
    }

    private fun toSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        setScreenLauncher.launch(intent)
    }

    private fun validateGalleryPerm() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        PermissionManager.validate(
                activity = this,
                permission = permission,
                onPermNotGranted = { galleryPermLauncher.launch(permission) },
                onPermGranted = {  },
                onPermReqDisabled = { showGallerySettingDialog() }
        )
    }

    private fun showGallerySettingDialog() {
        setDialog = Dialog(this).setup(
                type = DialogType.WRAPPED_BUTTONS,
                title = R.string.dialog_set_gallery_title,
                desc = R.string.dialog_set_gallery_desc,
                buttonMdl1 = ButtonModel(
                        title = R.string.dialog_set_gallery_btn_title,
                        onClick = {}
                )
        )
        setDialog.show()
    }

}