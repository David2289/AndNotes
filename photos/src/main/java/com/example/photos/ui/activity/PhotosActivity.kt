package com.example.photos.ui.activity

import android.Manifest
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.commons.ui.component.extensions.DialogExtensions.Companion.setup
import com.example.commons.ui.model.button.ButtonModel
import com.example.photos.R
import com.example.photos.databinding.PhotosActivityBinding
import com.example.photos.utility.manager.PermissionManager


class PhotosActivity: AppCompatActivity() {

    lateinit var binding: PhotosActivityBinding
    lateinit var photoPermResultLauncher: ActivityResultLauncher<Array<out String>>
    lateinit var photoChooserResultLauncher: ActivityResultLauncher<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val myVar: String
        val secondVar: String? = null
        myVar = secondVar ?: "Defualt value"


        photoPermResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
                { result ->
                    if (result.get(Manifest.permission.CAMERA) == true && result.get(Manifest.permission.CAMERA) == true) {
                        openPhotoChooser()
                    }
                }

        val photoChooserResultLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview())
                {

                }

        binding = DataBindingUtil.setContentView(this, R.layout.photos_activity)
        binding.imageContent.setOnClickListener {

            val dialog = Dialog(this).setup(
                    title = R.string.dialog_media_chooser_title,
                    desc = R.string.dialog_media_chooser_desc,
                    buttonMdl1 = ButtonModel(
                            title = R.string.dialog_media_chooser_btn_camera,
                            endIcon = R.drawable.ic_camera_black,
                            onClick = {}),
                    buttonMdl2 = ButtonModel(
                            title = R.string.dialog_media_chooser_btn_gallery,
                            endIcon = R.drawable.ic_folder_black,
                            onClick = {})
            )
            dialog.show()

//            val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
//            PermissionManager.validate(
//                    activity = this,
//                    permissions = permissions,
//                    onPermGranted = { openPhotoChooser() },
//                    onPermDenied = { photoPermResultLauncher.launch(permissions) },
//                    onPermReqDisabled = {  }
//            )
        }
        setContentView(binding.root)
    }

    private fun openPhotoChooser() {
        // Without uri parameter the option is Google Drive:
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Broadcast Receiver
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                TODO("Not yet implemented")
            }
        }

        // Intent to open a system dialog chooser between camera or gallery:
        val chooser = Intent.createChooser(galleryIntent, "Some text here", )
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

        // TODO correct deprecated
        startActivity(chooser)
    }

}