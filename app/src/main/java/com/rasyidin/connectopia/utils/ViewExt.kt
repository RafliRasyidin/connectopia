package com.rasyidin.connectopia.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

fun Context.showStatusBar() {
    val window = this.findActivity()?.window
    val insetsController = WindowCompat.getInsetsController(window ?: return, window.decorView)
    insetsController.apply {
        show(WindowInsetsCompat.Type.statusBars())
        show(WindowInsetsCompat.Type.navigationBars())
    }
}

fun Context.hideStatusBar() {
    val window = this.findActivity()?.window
    val insetsController = WindowCompat.getInsetsController(window ?: return, window.decorView)
    insetsController.apply {
        hide(WindowInsetsCompat.Type.statusBars())
        hide(WindowInsetsCompat.Type.navigationBars())
    }
}