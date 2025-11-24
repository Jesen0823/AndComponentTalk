package com.jesen.personal.comment.mvp.view

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jesen.component_annotation.ComARouter
import com.jesen.personal.R
import com.jesen.personal.comment.adapter.CommentAdapter
import com.jesen.personal.comment.data.model.Comment
import com.jesen.personal.comment.mvp.contract.CommentContract
import com.jesen.personal.comment.mvp.presenter.CommentPresenter
import com.jesen.personal.comment.utils.ToastUtil
import com.jesen.personal.databinding.ActivityCommentBinding

@ComARouter(path = "/personal/CommentActivity")
class CommentActivity : AppCompatActivity(), CommentContract.View {
    private lateinit var binding: ActivityCommentBinding
    private lateinit var presenter: CommentContract.Presenter
    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = CommentPresenter()
        presenter.attachView(this)

        initListView()

        presenter.loadFirstPage()
    }

    private fun initListView() {
        adapter = CommentAdapter(this@CommentActivity) {
            presenter.loadNextPage()
        }
        binding.lvComment.adapter = adapter

        // 优化点 3：预取优化（滚动监听辅助）
        // 滚动监听：确保滑动到底部时加载更多（兜底，预取已在适配器中实现）
        binding.lvComment.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                // 滑动停止时，若已到底部且有更多数据，加载下一页
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    val lastVisiblePosition = binding.lvComment.lastVisiblePosition
                    if (lastVisiblePosition == adapter.count - 1 && !adapter.isLoading && adapter.hasMore) {
                        presenter.loadNextPage()
                    }
                }
            }
        })
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.lvComment.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.lvComment.visibility = View.VISIBLE
    }

    override fun showLoadMoreLoading() {
        adapter.isLoading = true
    }

    override fun hideLoadMoreLoading() {
        adapter.isLoading = false
    }

    override fun onLoadSuccess(
        comments: List<Comment>,
        hasMore: Boolean
    ) {
        adapter.addData(comments, hasMore)
    }

    override fun onLoadFailed(message: String) {
        ToastUtil.show(this, message)
    }

    override fun showNoMoreData() {
        ToastUtil.show(this, "已加载全部数据")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}