package com.jesen.personal.comment.data.api

import com.jesen.personal.comment.data.model.Comment
import retrofit2.http.GET

interface ApiService {

    @GET("comments")
    suspend fun getComments():List<Comment>
}