package com.example.chat.view

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

abstract class BaseActivity: AppCompatActivity() {
    abstract fun permissionGranted(requestCode: Int) //권한이 승인되어있음
    abstract fun permissionDenied(requestCode: Int)

    //권한 검사
    fun requirePermissions(permission:Array<String>, requestCode:Int) {
        //api 버전이 마시멜로 미만이면 권한처리가 필요없다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            permissionGranted(requestCode)
        }
        else{
            //권한이 없으면 권한 요청 -> 팝업
            ActivityCompat.requestPermissions(this, permission, requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults.all { it == PackageManager.PERMISSION_GRANTED }) { //배열 안에 있는 값이 전부 승인이 되어있는지 확인
            permissionGranted(requestCode)
        }
        else{
            permissionDenied(requestCode)
        }
    }
}