package com.jesen.personal.comment.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jesen.personal.R

/**
 * Glide 工具类：封装图片加载，优化加载性能
 */
object GlideUtil {
    // 图片加载选项：圆形裁剪、占位图、错误图
    private val avatarOptions = RequestOptions()
        .circleCrop()
        .placeholder(R.drawable.ic_avatar_placeholder)
        .error(R.drawable.ic_avatar_error)
        .override(80, 80) // 固定尺寸，避免图片过大导致 OOM

    /**
     * 加载头像（用 email 生成 Gravatar 头像链接）
     */
    fun loadAvatar(context: Context, email: String, imageView: ImageView) {
        // 用 email 的 MD5 生成 Gravatar 头像（模拟图片加载场景）
        val gravatarUrl = "https://www.gravatar.com/avatar/${md5(email.lowercase())}?s=80"
        Glide.with(context)
            .load(gravatarUrl)
            .apply(avatarOptions)
            .into(imageView)
    }

    /**
     * MD5 加密（简化实现，仅用于生成 Gravatar 链接）
     */
    private fun md5(input: String): String {
        val md = java.security.MessageDigest.getInstance("MD5")
        val bytes = md.digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}