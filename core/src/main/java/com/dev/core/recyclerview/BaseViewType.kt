@file:Suppress("UNCHECKED_CAST")

package com.dev.core.recyclerview

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.dev.core.animations.AlphaInAnimation
import com.dev.core.animations.BaseAnimation
import com.dev.core.recyclerview.BaseViewType.EMPTY_VIEW
import com.dev.core.recyclerview.BaseViewType.LOADING_VIEW

object BaseViewType {
    const val LOADING_VIEW = 0x00000222
    const val EMPTY_VIEW = 0x00000555
}

interface BaseRVListener {
    var mItemClickListener: BaseRecyclerViewAdapter.OnItemClickListener?
    var mItemLongClickListener: BaseRecyclerViewAdapter.OnItemLongClickListener?
    var mItemChildClickListener: BaseRecyclerViewAdapter.OnItemChildClickListener?
}

interface RequestLoadMoreListener {
    fun onLoadMoreRequested()
}

abstract class BaseRecyclerViewAdapter<T, K : BaseViewHolder>(var mData: MutableList<T>) : RecyclerView.Adapter<K>(),
    BaseRVListener {

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface OnItemChildClickListener {
        fun onItemChildClick(view: View, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    override var mItemClickListener: OnItemClickListener? = null

    override var mItemLongClickListener: OnItemLongClickListener? = null

    override var mItemChildClickListener: OnItemChildClickListener? = null

    var mRequestLoadMoreListener: RequestLoadMoreListener? = null

    private var mNextLoadEnable: Boolean = false
    private var mLoadMoreEnable: Boolean = false
    private var mLoading: Boolean = false

    var isShimmer: Boolean = false
    private var mObj: T? = null
    val EMPTY_SIZE = 10


    private var mRecyclerView: RecyclerView? = null

    private var mOpenAnimationEnable: Boolean = false

    private var mCustomAnimation: BaseAnimation = AlphaInAnimation()

    protected var mLastPosition: Int = -1

    private val mInterpolator = LinearInterpolator()
    private var mDuration = 300L

    open val mLoadMoreView = SimpleLoadMoreView()

    protected abstract fun convert(helper: K, item: T)

    protected abstract fun createHolder(parent: ViewGroup, viewType: Int): BaseViewHolder

    fun getItem(position: Int): T {
        return mData[position]
    }

    override fun getItemCount(): Int {
        return if (getEmptyViewCount() == 1) {
            1
        } else {
            mData.size + getLoadMoreViewCount()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K {
        var baseViewHolder: BaseViewHolder
        when (viewType) {
            LOADING_VIEW -> {
                baseViewHolder = getLoadingView(parent)
            }
            EMPTY_VIEW -> {
                baseViewHolder = BaseViewHolder(mEmptyLayout as View)
            }
            else -> {
                baseViewHolder = createHolder(parent, viewType)
                bindViewClickListener(baseViewHolder)
            }
        }
        baseViewHolder.setAdapterListener(this)
        return baseViewHolder as K
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        mItemLongClickListener = listener
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mItemClickListener = listener
    }

    private fun bindViewClickListener(baseViewHolder: BaseViewHolder?) {
        baseViewHolder?.let {
            baseViewHolder.itemView.setOnClickListener { v -> mItemClickListener?.onItemClick(v, baseViewHolder.layoutPosition) }
            baseViewHolder.itemView.setOnLongClickListener { v ->
                mItemLongClickListener?.onItemLongClick(v, baseViewHolder.layoutPosition)
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getEmptyViewCount() == 1) {
            EMPTY_VIEW
        } else if (position < mData.size) {
            super.getItemViewType(position)
        } else {
            LOADING_VIEW
        }
    }

    private var mEmptyLayout: FrameLayout? = null

    fun setEmptyView(emptyView: View) {
        var insert = false
        if (mEmptyLayout == null) {
            mEmptyLayout = FrameLayout(emptyView.context)
            val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            val lp = emptyView.layoutParams
            if (lp != null) {
                layoutParams.width = lp.width
                layoutParams.height = lp.height
            }
            mEmptyLayout?.layoutParams = layoutParams
            insert = true
        }
        mEmptyLayout?.removeAllViews()
        mEmptyLayout?.addView(emptyView)
        if (insert) {
            if (getEmptyViewCount() == 1) {
                notifyItemInserted(0)
            }
        }
    }

    fun getEmptyViewCount(): Int {
        if (mEmptyLayout == null || mEmptyLayout?.childCount == 0) {
            return 0
        }

        return if (mData.isNotEmpty()) {
            0
        } else 1
    }

    open fun getItemView(@LayoutRes layoutResId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
    }

    private fun getLoadingView(parent: ViewGroup): BaseViewHolder {
        val view = getItemView(mLoadMoreView.layoutId, parent)
        val holder = BaseViewHolder(view)
        return holder
    }

    open fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): BaseViewHolder {
        return BaseViewHolder(getItemView(layoutResId, parent))
    }

    override fun onBindViewHolder(holder: K, position: Int) {
        var viewType = holder.itemViewType
        autoLoadMore(position)
        when (viewType) {
            0 -> convert(holder, getItem(position))
            LOADING_VIEW -> mLoadMoreView.convert(holder)
            EMPTY_VIEW -> {
            }
            else -> convert(holder, getItem(position))
        }
    }

    override fun onViewAttachedToWindow(holder: K) {
        super.onViewAttachedToWindow(holder)
        addAnimation(holder)
    }

    fun openLoadAnimation(animation: BaseAnimation, duration: Long = 300) {
        this.mOpenAnimationEnable = true
        this.mCustomAnimation = animation
        this.mDuration = duration
    }

    private fun addAnimation(holder: RecyclerView.ViewHolder) {
        if (mOpenAnimationEnable) {
            if (holder.layoutPosition > mLastPosition) {
                for (anim in mCustomAnimation.getAnimators(holder.itemView)) {
                    startAnim(anim)
                }
                mLastPosition = holder.layoutPosition
            }
        }
    }

    private fun startAnim(anim: Animator) {
        anim.setDuration(mDuration).start()
        anim.interpolator = mInterpolator
    }

    private fun openLoadMore(requestLoadMoreListener: RequestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener
        mNextLoadEnable = true
        mLoadMoreEnable = true
        mLoading = false
    }

    open fun getLoadMoreViewCount(): Int {
        if (mRequestLoadMoreListener == null || !mLoadMoreEnable) {
            return 0
        }
        if (!mNextLoadEnable && mLoadMoreView.isLoadEndMoreGone) {
            return 0
        }
        return if (mData.isEmpty()) {
            0
        } else 1
    }

    fun setOnLoadMoreListener(requestLoadMoreListener: RequestLoadMoreListener, recyclerView: RecyclerView) {
        openLoadMore(requestLoadMoreListener)
        if (mRecyclerView == null) {
            mRecyclerView = recyclerView
        }
    }

    private var mPreLoadNumber = 2

    fun setPreLoadNumber(preLoadNumber: Int) {
        if (preLoadNumber > 1) {
            mPreLoadNumber = preLoadNumber
        }
    }

    private fun autoLoadMore(position: Int) {
        if (getLoadMoreViewCount() == 0) {
            return
        }
        if (position < itemCount - mPreLoadNumber) {
            return
        }
        if (mLoadMoreView.loadMoreStatus != LoadMoreView.STATUS_DEFAULT) {
            return
        }
        mLoadMoreView.loadMoreStatus = LoadMoreView.STATUS_LOADING
        if (!mLoading) {
            mLoading = true
            if (mRecyclerView != null) {
                mRecyclerView?.post { mRequestLoadMoreListener?.onLoadMoreRequested() }
            } else {
                mRequestLoadMoreListener?.onLoadMoreRequested()
            }
        }
    }


    /**
     * Gets to load more locations
     *
     * @return
     */
    private fun getLoadMoreViewPosition(): Int {
        return mData.size
    }

    /**
     * @return Whether the Adapter is actively showing load
     * progress.
     */
    fun isLoading(): Boolean {
        return mLoading
    }

    /**
     * Refresh end, no more data
     */
    fun loadMoreEnd() {
        loadMoreEnd(false)
    }

    /**
     * Refresh end, no more data
     *
     * @param gone if true gone the load more view
     */
    private fun loadMoreEnd(gone: Boolean) {
        if (getLoadMoreViewCount() == 0) {
            return
        }
        mLoading = false
        mNextLoadEnable = false
        mLoadMoreView.setLoadMoreEndGone(gone)
        if (gone) {
            notifyItemRemoved(getLoadMoreViewPosition())
        } else {
            mLoadMoreView.loadMoreStatus = LoadMoreView.STATUS_END
            notifyItemChanged(getLoadMoreViewPosition())
        }
    }

    /**
     * Refresh complete
     */
    fun loadMoreComplete() {
        if (getLoadMoreViewCount() == 0) {
            return
        }
        mLoading = false
        mNextLoadEnable = true
        mLoadMoreView.loadMoreStatus = LoadMoreView.STATUS_DEFAULT
        notifyItemChanged(getLoadMoreViewPosition())
    }

    /**
     * Refresh failed
     */
    fun loadMoreFail() {
        if (getLoadMoreViewCount() == 0) {
            return
        }
        mLoading = false
        mLoadMoreView.loadMoreStatus = LoadMoreView.STATUS_FAIL
        notifyItemChanged(getLoadMoreViewPosition())
    }

    fun addData(items: List<T>) {
        isShimmer = false
        mData.addAll(items)
        notifyDataSetChanged()
    }

    fun addData(item: T) {
        isShimmer = false
        mData.add(item)
        notifyDataSetChanged()
    }

    fun setData(items: List<T>) {
        isShimmer = false
        mData = items.toMutableList()
        notifyDataSetChanged()
    }

    fun setDataAtPosition(position: Int, item: T) {
        isShimmer = false
        mData[position] = item
        notifyItemChanged(position)
    }

    fun clear() {
        mData.clear()
        notifyDataSetChanged()
    }

    fun setShimmerEnabled(isShimmer: Boolean, obj: T) {
        this.isShimmer = isShimmer
        this.mObj = obj
    }
}