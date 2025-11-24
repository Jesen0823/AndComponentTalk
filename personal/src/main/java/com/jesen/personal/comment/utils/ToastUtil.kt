package com.jesen.personal.comment.utils

import android.content.Context
import android.widget.Toast

object ToastUtil {
    private var toast: Toast? = null

    /**
     * 显示 Toast，避免连续点击创建多个 Toast
     */
    fun show(context: Context, message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}