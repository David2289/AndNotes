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
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.*
import com.example.commons.ui.component.extensions.setupListButtons
import com.example.commons.ui.component.extensions.setupOneButtons
import com.example.commons.ui.component.extensions.setupTwoButtons
import com.example.commons.ui.model.button.ButtonModel
import com.example.photos.R
import com.example.photos.business.datasource.local.androom.entity.PictureEntity
import com.example.photos.databinding.PhotosActivityBinding
import com.example.photos.ui.adapter.PhotosAdapter
import com.example.photos.ui.viewmodel.PhotosViewModel
import com.example.photos.utility.manager.PermissionManager
import org.koin.android.ext.android.get


class PhotosActivity: AppCompatActivity() {

    var viewModel = get<PhotosViewModel>()
    lateinit var binding: PhotosActivityBinding
    lateinit var cameraPermLauncher: ActivityResultLauncher<String>
    lateinit var galleryWritePermLauncher: ActivityResultLauncher<String>
    lateinit var galleryReadPermLauncher: ActivityResultLauncher<String>
    lateinit var takePictureLauncher: ActivityResultLauncher<Uri>
    lateinit var setScreenLauncher: ActivityResultLauncher<Intent>
    lateinit var mediaDialog: Dialog
    lateinit var setDialog: Dialog
    lateinit var removeDialog: Dialog
    var adapter: PhotosAdapter? = null
    var imageUri: Uri? = null
    private var currentPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.photos_activity)
        binding.viewModel = viewModel
        binding.activity = this

        val picturesObserver = Observer<ArrayList<PictureEntity>> { result -> configUI(result) }
        viewModel.pictures.observe(this, picturesObserver)

        viewModel.getPictures()

        cameraPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                galleryWritePermLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
        galleryWritePermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openCamera()
            }
        }
        galleryReadPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
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


    private fun configUI(pictureList: ArrayList<PictureEntity>) {
        binding.photosContent.visibility = if (pictureList.isEmpty()) View.GONE else View.VISIBLE
        binding.emptyContent.visibility = if (pictureList.isEmpty()) View.VISIBLE else View.GONE
        if (pictureList.isNotEmpty()) {
            configPhotosUI(pictureList)
        }
    }


    private fun configPhotosUI(pictureList: ArrayList<PictureEntity>) {
        if (adapter == null) {
            adapter = PhotosAdapter(pictureList)
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
            pictureList.clear()
            pictureList.addAll(pictureList)
            adapter?.notifyDataSetChanged()
        }
    }


    // region DIALOGS

    fun openMediaChooser() {
        val btnMdl1 = ButtonModel(
            title = R.string.dialog_media_chooser_btn_camera,
            endIcon = R.drawable.ic_camera_black,
            onClick = {
                checkCameraPerm()
                mediaDialog.dismiss()
            })

        val btnMdl2 = ButtonModel(
            title = R.string.dialog_media_chooser_btn_gallery,
            endIcon = R.drawable.ic_folder_black,
            onClick = {
                checkGalleryReadPerm()
                mediaDialog.dismiss()
            })

        mediaDialog = Dialog(this).setupListButtons(
            title = R.string.dialog_media_chooser_title,
            desc = R.string.dialog_media_chooser_desc,
            buttonMdl1 = btnMdl1,
            buttonMdl2 = btnMdl2
        )
        mediaDialog.show()
    }


    fun openRemoveDialog() {
        val btnMdl1 = ButtonModel(
            title = R.string.commons_no,
            onClick = { removeDialog.dismiss() }
        )
        val btnMdl2 = ButtonModel(
            title = R.string.commons_yes,
            onClick = {
                viewModel.pictures.value?.let {
                    removeDialog.dismiss()
                    viewModel.deletePicture(it[currentPosition])
                    viewModel.getPictures()
                }
            }
        )
        removeDialog = Dialog(this).setupTwoButtons(
            title = R.string.dialog_remove_photo_title,
            desc = R.string.dialog_remove_photo_desc,
            btnModel1 = btnMdl1,
            btnModel2 = btnMdl2
        )
        removeDialog.show()
    }


    private fun showCameraSettingDialog() {
        val btnMdl = ButtonModel(
            title = R.string.dialog_set_camera_btn_title,
            onClick = { toSettings() })
        setDialog = Dialog(this).setupOneButtons(
            title = R.string.dialog_set_camera_title,
            desc = R.string.dialog_set_camera_desc,
            btnModel = btnMdl
        )
        setDialog.show()
    }


    private fun showGallerySettingDialog() {
        setDialog = Dialog(this).setupOneButtons(
            title = R.string.dialog_set_gallery_title,
            desc = R.string.dialog_set_gallery_desc,
            btnModel = ButtonModel(
                title = R.string.dialog_set_gallery_btn_title,
                onClick = { toSettings() }
            )
        )
        setDialog.show()
    }

    //endregion


    // region CHECK PERMISSIONS

    private fun checkCameraPerm() {
        val permission = Manifest.permission.CAMERA
        PermissionManager.check(
            activity = this,
            permission = permission,
            onPermNone = { cameraPermLauncher.launch(permission) },
            onPermDenied = { cameraPermLauncher.launch(permission) },
            onPermGranted = { checkGalleryWritePerm() },
            onPermReqDisabled = { showCameraSettingDialog() }
        )
    }


    private fun checkGalleryWritePerm() {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        PermissionManager.check(
            activity = this,
            permission = permission,
            onPermNone = { galleryWritePermLauncher.launch(permission) },
            onPermDenied = { galleryWritePermLauncher.launch(permission) },
            onPermGranted = { openCamera() },
            onPermReqDisabled = { showGallerySettingDialog() }
        )
    }


    private fun checkGalleryReadPerm() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        PermissionManager.check(
            activity = this,
            permission = permission,
            onPermNone = { galleryReadPermLauncher.launch(permission) },
            onPermDenied = { galleryReadPermLauncher.launch(permission) },
            onPermGranted = {  },
            onPermReqDisabled = { showGallerySettingDialog() }
        )
    }

    //endregion


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

}