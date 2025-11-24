package com.jesen.personal.comment.data.repository

import com.jesen.personal.comment.data.model.Comment

/**
 * 数据仓库接口：定义数据获取方式
 */
interface CommentRepository {
    /**
     * 获取所有评论数据
     */
    suspend fun getAllComments(): List<Comment>
}