package com.jesen.personal.comment.mvp.presenter

import com.jesen.personal.comment.data.model.Comment
import com.jesen.personal.comment.data.repository.CommentRepository
import com.jesen.personal.comment.data.repository.CommentRepositoryImpl
import com.jesen.personal.comment.mvp.contract.CommentContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Presenter 实现：连接 View 和 Model，处理业务逻辑
 */
class CommentPresenter : CommentContract.Presenter {
    private var view: CommentContract.View? = null
    private val repository: CommentRepository = CommentRepositoryImpl()

    private val pageSize = 20
    private var currentPage = 0
    private var allComments: List<Comment> = emptyList()
    private var isLoading = false

    override fun attachView(view: CommentContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadFirstPage() {
        if (isLoading) return
        isLoading = true
        view?.showLoading()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                allComments = repository.getAllComments()
                val firstPageData = allComments.take(pageSize)
                val hasMore = allComments.size > pageSize
                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                    view?.onLoadSuccess(firstPageData, hasMore)
                    currentPage = 1
                    isLoading = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                    view?.onLoadFailed("加载失败：${e.message}")
                    isLoading = false
                }
            }
        }
    }

    override fun loadNextPage() {
        if (isLoading) return
        isLoading = true
        view?.showLoadMoreLoading()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val startIndex = currentPage * pageSize
                val endIndex = (currentPage + 1) * pageSize
                // 截取数据
                val nextPageData = if (endIndex <= allComments.size) {
                    allComments.subList(startIndex, endIndex)
                } else {
                    allComments.subList(startIndex, allComments.size)
                }
                val hasMore = endIndex < allComments.size
                withContext(Dispatchers.Main) {
                    view?.hideLoadMoreLoading()
                    view?.onLoadSuccess(nextPageData, hasMore)
                    currentPage++
                    isLoading = false
                    // 无更多数据时显示提示
                    if (!hasMore) {
                        view?.showNoMoreData()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.hideLoadMoreLoading()
                    view?.onLoadFailed("加载更多失败：${e.message}")
                    isLoading = false
                }
            }
        }
    }

}