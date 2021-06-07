package me.arunsharma.devupdates.ui.fragments.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.recyclerview.BaseViewHolder
import com.dev.services.models.ServiceItem
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.ItemFeedItemBinding

class VHFeedItem(val binding: ItemFeedItemBinding) : BaseViewHolder(binding.root)

class FeedAdapter(items: List<ServiceItem>, val allowBookmarks: Boolean = true) :
    BaseRecyclerViewAdapter<ServiceItem, VHFeedItem>(items.toMutableList()) {
    override fun convert(helper: VHFeedItem, item: ServiceItem) {
        helper.binding.tvTitle.text = item.title
        helper.binding.tvSubTitle.text = item.description
        helper.binding.tvTopTitle.text = item.topTitleText
        helper.binding.tvFooter.text = item.likes
        if (allowBookmarks) {
            helper.binding.ivBookmark.visibility = View.VISIBLE
            helper.binding.ivBookmark.isSelected = item.isBookmarked
            helper.addOnClickListener(R.id.ivBookmark)
        } else {
            helper.binding.ivBookmark.visibility = View.GONE
        }
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return VHFeedItem(
            ItemFeedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun updateItem(item: ServiceItem) {
        val index = mData.indexOfFirst {
            item.actionUrl == it.actionUrl
        }
        if (index > 0) {
            mData[index] = item
            notifyItemChanged(index)
        }
    }

}