package com.example.photos.ui.activity

import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.commons.ui.component.enums.DialogType
import com.example.commons.ui.component.extensions.DialogExtensions.Companion.setup
import com.example.commons.ui.model.button.ButtonModel
import com.example.photos.R
import com.example.photos.databinding.PhotosActivityBinding
import com.example.photos.utility.manager.PermissionManager
import java.io.FileNotFoundException
import java.io.InputStream


class PhotosActivity: AppCompatActivity() {

    lateinit var binding: PhotosActivityBinding
    lateinit var cameraPermLauncher: ActivityResultLauncher<String>
    lateinit var galleryPermLauncher: ActivityResultLauncher<String>
    lateinit var takePictureLauncher: ActivityResultLauncher<Uri>
    lateinit var setScreenLauncher: ActivityResultLauncher<Intent>
    lateinit var mediaDialog: Dialog
    lateinit var setDialog: Dialog
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                binding.imageContent.visibility = View.VISIBLE
                binding.emptyContent.visibility = View.GONE
                binding.picture.setImageURI(imageUri)

//                var bitmap: Bitmap? = null
//                try {
//                    imageUri?.let {
//                        val inputStream = contentResolver.openInputStream(it)
//                        bitmap = BitmapFactory.decodeStream(inputStream)
////                        String imageSource = ImageBitmapString.BitMapToString(bitmap)
////                        imageSources.add(imageSource)
//                    }
//                }
//                catch (e: FileNotFoundException) {
//                    e.printStackTrace()
//                }
//
//                bitmap?.let { binding.picture.setImageBitmap(it) }
            }
        }

        setScreenLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            print("Settings results")
        }

        binding = DataBindingUtil.setContentView(this, R.layout.photos_activity)
        binding.emptyContent.setOnClickListener {
            mediaDialog = Dialog(this).setup(
                    type = DialogType.EXPANDED_BUTTONS,
                    title = R.string.dialog_media_chooser_title,
                    desc = R.string.dialog_media_chooser_desc,
                    buttonMdl1 = ButtonModel(
                            title = R.string.dialog_media_chooser_btn_camera,
                            endIcon = R.drawable.ic_camera_black,
                            onClick = {
                                validateCameraPerm()
                                mediaDialog.dismiss()
                            }),
                    buttonMdl2 = ButtonModel(
                            title = R.string.dialog_media_chooser_btn_gallery,
                            endIcon = R.drawable.ic_folder_black,
                            onClick = {
                                validateGalleryPerm()
                                mediaDialog.dismiss()
                            })
            )
            mediaDialog.show()

        }
        setContentView(binding.root)
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
        setDialog = Dialog(this).setup(
                type = DialogType.WRAPPED_BUTTONS,
                title = R.string.dialog_set_camera_title,
                desc = R.string.dialog_set_camera_desc,
                buttonMdl1 = ButtonModel(
                        title = R.string.dialog_set_camera_btn_title,
                        onClick = { toSettings() }
                )
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