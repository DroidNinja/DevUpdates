package com.dev.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.dev.core.R
import com.dev.core.view.shimmer.ShimmerView
import java.util.*

class ProgressLayout : RelativeLayout {

    private var inflater: LayoutInflater? = null
    private var layoutParams: LayoutParams? = null
    private var loadingGroup: View? = null
    private var errorGroup: View? = null

    private var loadingLayout: RelativeLayout? = null
    private var errorLayout: RelativeLayout? = null
    private var errorTextView: TextView? = null
    private var errorButton: TextView? = null

    private val contentViews = ArrayList<View>()

    var currentState = State.LOADING
        private set

    val isContent: Boolean
        get() = currentState == State.CONTENT

    val isLoading: Boolean
        get() = currentState == State.LOADING

    val isError: Boolean
        get() = currentState == State.ERROR

    interface LoadingView {

        val context: Context
        fun showLoading()

        fun showContent()

        fun showError(messageId: Int, listener: View.OnClickListener)
    }

    enum class State {
        LOADING, CONTENT, ERROR
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)

        if (child.tag == null || child.tag != LOADING_TAG && child.tag != ERROR_TAG) {
            contentViews.add(child)
        }
    }

    fun showContent() {
        currentState = State.CONTENT
        this@ProgressLayout.hideLoadingView()
        this@ProgressLayout.hideErrorView()
        this@ProgressLayout.setContentVisibility(true)
    }

    fun showLoading(showShimmer: Boolean = false, shimmerLayout: Int = 0) {
        showLoading(showShimmer, arrayOf(shimmerLayout))
    }

    fun showLoading(showShimmer: Boolean = false, shimmerLayouts: Array<Int>) {
        currentState = State.LOADING
        if (showShimmer) {
            this@ProgressLayout.showShimmerView(shimmerLayouts)
        } else {
            this@ProgressLayout.showLoadingView()
        }
        this@ProgressLayout.hideErrorView()
        this@ProgressLayout.setContentVisibility(false)
    }

    private fun showLoadingView() {

        if (loadingGroup == null) {
            loadingGroup = inflater?.inflate(R.layout.layout_progress_loading, null)
            val loadingLayout = loadingGroup?.findViewById<RelativeLayout>(R.id.llRootLayout)
            loadingLayout?.tag = LOADING_TAG

            layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)

            addView(loadingLayout, layoutParams)
        } else {
            loadingLayout?.visibility = View.VISIBLE
        }
    }

    private fun showShimmerView(layouts: Array<Int>) {
        if (loadingGroup == null) {
            loadingGroup = inflater?.inflate(R.layout.layout_shimmer_view, null)
            val shimmerView = loadingGroup?.findViewById<ShimmerView>(R.id.shimmerView)
            shimmerView?.setShimmerLayout(layouts)
            shimmerView?.tag = ERROR_TAG
            layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
            addView(shimmerView, layoutParams)
        } else {
            loadingGroup?.visibility = View.VISIBLE
        }
    }

    fun showError(errorLayout: View) {
        currentState = State.ERROR
        val errorView = findViewWithTag<View>(ERROR_TAG)
        if (errorGroup != null && errorView != null) {
            removeView(errorView)
        }
        hideLoadingView()
        errorLayout.tag = ERROR_TAG
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        this.errorGroup = errorLayout
        addView(errorGroup, layoutParams)
        setContentVisibility(false)
    }

    fun showError(@StringRes stringId: Int, onClickListener: View.OnClickListener?) {
        showError(resources.getString(stringId), onClickListener)
    }

    fun showError(errorMessage: String, onClickListener: View.OnClickListener?) {
        currentState = State.ERROR
        hideLoadingView()
        showErrorView()

        errorTextView?.text = errorMessage

        if (onClickListener != null) {
            errorButton?.visibility = View.VISIBLE
            errorButton?.setOnClickListener(onClickListener)
        } else {
            errorButton?.visibility = View.GONE
        }
        setContentVisibility(false)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (errorButton != null) errorButton!!.setOnClickListener(null)
    }

    private fun showErrorView() {
        if (errorGroup == null) {
            errorGroup = inflater?.inflate(R.layout.layout_progress_error, null)
            errorGroup?.tag = ERROR_TAG
//            errorLayout = errorGroup?.
//
//            errorTextView = errorGroup?.findViewById(R.id.tvError)
//            errorButton = errorGroup?.findViewById(R.id.tvRetry)
//
//            layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT)

            addView(errorGroup, layoutParams)
        } else {
            errorLayout?.visibility = View.VISIBLE
        }
    }

    private fun hideLoadingView() {
        if (loadingGroup != null && loadingGroup?.visibility != View.GONE) {
            loadingGroup?.visibility = View.GONE
        }
    }

    private fun hideErrorView() {
        if (errorGroup?.visibility != View.GONE) {
            errorGroup?.visibility = View.GONE
        }
    }

    private fun setContentVisibility(visible: Boolean) {
        for (contentView in contentViews) {
            contentView.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }

    companion object {
        private const val LOADING_TAG = "ProgressLayout.LOADING_TAG"
        private const val ERROR_TAG = "ProgressLayout.ERROR_TAG"
    }
}