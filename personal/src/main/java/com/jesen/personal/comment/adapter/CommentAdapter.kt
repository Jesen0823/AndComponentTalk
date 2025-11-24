package com.jesen.personal.comment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jesen.personal.R
import com.jesen.personal.comment.data.model.Comment
import com.jesen.personal.comment.utils.GlideUtil
import com.jesen.personal.databinding.ItemCommentType1Binding
import com.jesen.personal.databinding.ItemCommentType2Binding

class CommentAdapter(
    private var context: Context,
    private val onLoadMore: () -> Unit
) : BaseAdapter() {
    // 设置加载状态（避免重复预取）
    var isLoading = false
    private val dataList = mutableListOf<Comment>()
    var hasMore = true

    // 预加载阈值，距离底部3个Item触发
    private val prefetchThreshold = 3

    companion object {
        private const val VIEW_TYPE_1 = 0 // 普通视图类型
        private const val VIEW_TYPE_2 = 1 // 特殊视图类型
    }

    // 添加数据
    fun addData(newData: List<Comment>, hasMore: Boolean) {
        this.hasMore = hasMore
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    // 重置数据（下拉刷新时调用，本案例未实现，预留接口）
    fun resetData(newData: List<Comment>, hasMore: Boolean) {
        dataList.clear()
        addData(newData, hasMore)
    }

    override fun getViewTypeCount(): Int = 2

    override fun getItemViewType(position: Int): Int {
        return if (dataList[position].id % 2 == 0) VIEW_TYPE_2 else VIEW_TYPE_1
    }

    override fun getCount(): Int = dataList.size + if (hasMore) 1 else 0 // 加载更多项占 1 个位置

    override fun getItem(position: Int): Comment? {
        return if (position < dataList.size) dataList[position] else null
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        // 优化点 3：预取优化
        // 距离底部 prefetchThreshold 个 item 且有更多数据时，触发预加载
        if (position >= dataList.size - prefetchThreshold && hasMore && !isLoading) {
            onLoadMore.invoke() // 触发加载更多
        }
        // 加载更多项（底部显示加载中）
        if (position == dataList.size) {
            return LayoutInflater.from(context).inflate(R.layout.item_load_more, parent, false)
        }
        val comment = dataList[position]
        val viewType = getItemViewType(position)
        var holder: BaseViewHolder
        var view = convertView

        // 根据视图类型复用 convertView 和 ViewHolder
        if (view == null) {
            // 无缓存，创建新视图和 ViewHolder
            view = when (viewType) {
                VIEW_TYPE_1 -> {
                    val binding =
                        ItemCommentType1Binding.inflate(LayoutInflater.from(context), parent, false)
                    holder = CommentViewHolderType1(binding)
                    binding.root.tag = holder // 缓存 ViewHolder 到 View 的 tag
                    binding.root
                }

                VIEW_TYPE_2 -> {
                    val binding =
                        ItemCommentType2Binding.inflate(LayoutInflater.from(context), parent, false)
                    holder = CommentViewHolderType2(binding)
                    binding.root.tag = holder
                    binding.root
                }

                else -> throw IllegalArgumentException("未知视图类型：$viewType")
            }
        } else {
            // 有缓存，直接获取 ViewHolder
            holder = view.tag as BaseViewHolder
        }

        // 绑定数据（仅做简单赋值，无耗时操作）
        holder.bindData(comment)

        return view
    }

    // 优化点 1：ViewHolder 模式
    //  视图持有者基类
    abstract class BaseViewHolder(itemView: View) {
        abstract fun bindData(comment: Comment)
    }

    inner class CommentViewHolderType1(private val binding: ItemCommentType1Binding) :
        BaseViewHolder(binding.root) {
        override fun bindData(comment: Comment) {
            binding.tvName.text = comment.name
            binding.tvEmail.text = comment.email
            binding.tvBody.text = comment.body

            // 图片加载（异步，Glide 自动缓存）
            GlideUtil.loadAvatar(context = context, comment.email, binding.ivAvatar)
        }
    }

    inner class CommentViewHolderType2(private val binding: ItemCommentType2Binding) :
        BaseViewHolder(binding.root) {
        override fun bindData(comment: Comment) {
            binding.tvNameType2.text = comment.name
            binding.tvEmailType2.text = comment.email
            binding.tvBodyType2.text = comment.body

            // 异步加载图片
            GlideUtil.loadAvatar(context, comment.email, binding.ivAvatarType2)
        }
    }
}