package com.thanh_nguyen.test_count_down.utils

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity


fun checkPermission(
    activity: AppCompatActivity,
    permission: String,
    onGranted: () -> Unit,
    shouldShowRequestPermissionRationable: () -> Unit,
    requestPermission: (permission: String) -> Unit
){
    when {
        activity.checkSelfPermission(
            permission
        ) == PackageManager.PERMISSION_GRANTED -> onGranted.invoke()

        activity.shouldShowRequestPermissionRationale(permission) -> shouldShowRequestPermissionRationable.invoke()

        else -> {
           requestPermission.invoke(permission)
        }
    }
}