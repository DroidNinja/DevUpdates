package me.arunsharma.devupdates.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.recyclerview.BaseViewHolder
import com.dev.services.models.ServiceItem
import me.arunsharma.devupdates.databinding.ItemFeedItemBinding

class VHFeedItem(val binding: ItemFeedItemBinding) : BaseViewHolder(binding.root)

class FeedAdapter(items: List<ServiceItem>) :
    BaseRecyclerViewAdapter<ServiceItem, VHFeedItem>(items.toMutableList()) {
    override fun convert(helper: VHFeedItem, item: ServiceItem) {
        helper.binding.tvTitle.text = item.title
        helper.binding.tvSubTitle.text = item.description
        helper.binding.tvTopTitle.text = item.author
        helper.binding.tvFooter.text = item.likes
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

}