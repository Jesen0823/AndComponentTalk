package com.jesen.personal.comment.data.repository

import com.jesen.personal.comment.data.api.RetrofitClient
import com.jesen.personal.comment.data.model.Comment

/**
 * 数据仓库实现：处理网络请求和数据缓存
 */
class CommentRepositoryImpl : CommentRepository{

    // 内存缓存所有评论数据（仅一次网络请求）
    private var allComments: List<Comment>? = null

    override suspend fun getAllComments(): List<Comment> {
        // 优先从缓存获取，避免重复网络请求
        allComments?.let { return it }
        // 缓存无数据，发起网络请求
        val comments = RetrofitClient.apiService.getComments()
        // 缓存数据
        allComments = comments
        return comments
    }

}