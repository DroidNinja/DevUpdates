package com.dev.core.recyclerview

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView


open class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private var views: SparseArray<View> = SparseArray<View>()
    private var adapterListener: BaseRVListener? = null

    fun setGone(@IdRes viewId: Int, visible: Boolean): BaseViewHolder {
        val view = getView(viewId)
        view?.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun setVisible(@IdRes viewId: Int, visible: Boolean): BaseViewHolder {
        val view = getView(viewId)
        view?.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        return this
    }


    fun getView(@IdRes viewId: Int): View? {
        var view = views.get(viewId)
        if (view == null) {
            view = itemView.findViewById<View>(viewId)
            views.put(viewId, view)
        }
        return view
    }

    fun addOnClickListener(@IdRes viewId: Int): BaseViewHolder {
        val view = getView(viewId)
        view?.let {
            if (!it.isClickable) {
                it.isClickable = true
            }
            it.setOnClickListener { view ->
                adapterListener?.mItemChildClickListener?.onItemChildClick(view, adapterPosition)
            }
        }
        return this
    }

    fun setAdapterListener(baseRVListener: BaseRVListener) {
        this.adapterListener = baseRVListener
    }
}