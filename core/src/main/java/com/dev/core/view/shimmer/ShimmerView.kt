package com.dev.core.view.shimmer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.core.R
import com.facebook.shimmer.ShimmerFrameLayout


class ShimmerView : RecyclerView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        //disable touch
        return true
    }

    fun setShimmerLayout(layout: Int) {
        val shimmerItems = mutableListOf<ShimmerItem>()
        for (i in 0..10) {
            shimmerItems.add(ShimmerItem(layout))
        }
        adapter = ShimmerViewAdapter(shimmerItems)
    }

    fun setShimmerLayout(layouts: Array<Int>) {
        val shimmerItems = mutableListOf<ShimmerItem>()
        if (layouts.size == 1) {
            for (i in 0..10) {
                shimmerItems.add(ShimmerItem(layouts[0]))
            }
        } else {
            layouts.forEach {
                shimmerItems.add(ShimmerItem(it))
            }
        }
        adapter = ShimmerViewAdapter(shimmerItems)
    }
}

class ShimmerItem(val itemType: Int)

class ShimmerViewHolder(inflater: LayoutInflater, parent: ViewGroup, innerViewResId: Int) :
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.layout_shimmer, parent, false
        )
    ) {

    private val mShimmerLayout: ShimmerFrameLayout = itemView as ShimmerFrameLayout

    init {
        inflater.inflate(innerViewResId, mShimmerLayout, true)
    }

    fun start() {
        mShimmerLayout.startShimmer()
    }

    fun stop() {
        mShimmerLayout.stopShimmer()
    }
}

class ShimmerViewAdapter(val data: List<ShimmerItem>) : RecyclerView.Adapter<ShimmerViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return data[position].itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
        return ShimmerViewHolder(
            LayoutInflater.from(parent.context),
            parent, viewType
        )
    }

    override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {
        holder.start()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
