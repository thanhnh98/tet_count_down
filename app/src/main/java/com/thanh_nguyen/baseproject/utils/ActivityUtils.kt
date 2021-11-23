package com.thanh_nguyen.baseproject.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.addFragment(idFrame: Int, fragment: Fragment, backStackName: String? = null){
    supportFragmentManager.beginTransaction()
        .addToBackStack(backStackName)
        .add(idFrame, fragment)
        .commit()
}

fun AppCompatActivity.replaceFragment(idFrame: Int, fragment: Fragment, backStackName: String? = null){
    supportFragmentManager.beginTransaction()
        .addToBackStack(backStackName)
        .replace(idFrame, fragment)
        .commit()
}