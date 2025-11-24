package com.jesen.personal.comment.mvp.contract

import com.jesen.personal.comment.data.model.Comment

/**
 * MVP 契约接口，统一 View 和 Presenter 的交互
 */
interface CommentContract {
    /**
     * View 层接口：负责 UI 展示
     */
    interface View{
        // 显示加载中状态（首次加载）
        fun showLoading()

        // 隐藏加载中状态
        fun hideLoading()

        // 显示加载更多状态（列表底部）
        fun showLoadMoreLoading()

        // 隐藏加载更多状态
        fun hideLoadMoreLoading()

        // 加载成功：更新列表数据
        fun onLoadSuccess(comments: List<Comment>, hasMore: Boolean)

        // 加载失败
        fun onLoadFailed(message: String)

        // 显示无更多数据
        fun showNoMoreData()
    }

    /**
     * Presenter 层接口：负责业务逻辑
     */
    interface Presenter{
        // 绑定 View
        fun attachView(view: View)

        // 解绑 View（避免内存泄漏）
        fun detachView()

        // 首次加载数据
        fun loadFirstPage()

        // 加载更多数据
        fun loadNextPage()
    }
}