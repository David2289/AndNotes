package com.example.photos.utility.manager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.commons.utility.helper.SharedPrefUtils

class PermissionManager {

    companion object {

        /**
         * onPermReqDisabled: When user picked "no to ask again" to permission request.
         */
        fun validate(activity: Activity,
                     permissions: Array<out String>,
                     onPermGranted: () -> Unit,
                     onPermDenied: () -> Unit,
                     onPermReqDisabled: () -> Unit
        ) {

            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
                onPermGranted() // For Android 5 (API 21 to 22) App has permission from installing.
                return
            }

            val grantedList = ArrayList<String>()
            val deniedList = ArrayList<String>()
            val disabledList = ArrayList<String>()

            for (permission in permissions) {
                when(ContextCompat.checkSelfPermission(activity, permission)) {
                    PackageManager.PERMISSION_DENIED -> {
                        // shouldShowRequestPermissionRationale:
                        // returns true if user has denied the permission.
                        // return false in others cases (For example: First time requesting or user has choose "No to show again")
                        // Android docs says to call the method inside PERMISSION_DENIED
                        if (activity.shouldShowRequestPermissionRationale(permission)) {
                            deniedList.add(permission)
                        } else {
                            if (firstTimeReqPerm(activity, permission)) {
                                deniedList.add(permission)
                                saveNotFirstTimeReqPerm(activity, permission)
                            } else {
                                // If is not the first time asking permission this only can happens if user has choose "No to show again"
                                disabledList.add(permission)
                            }
                        }
                    }
                    PackageManager.PERMISSION_GRANTED -> grantedList.add(permission)
                }
            }

            // If every permission was granted:
            when {
                disabledList.size > 0 -> onPermReqDisabled()
                grantedList.size == permissions.size -> onPermGranted()
                else -> onPermDenied()
            }
        }

        private fun firstTimeReqPerm(context: Context, permission: String): Boolean {
            return SharedPrefUtils.getBooleanData(context, permission, true)
        }

        private fun saveNotFirstTimeReqPerm(context: Context, permission: String) {
            SharedPrefUtils.saveData(context, permission, false)
        }



    }

}