package me.arunsharma.devupdates.ui.fragments.addsource

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.recyclerview.BaseViewHolder
import com.dev.services.models.DataSource
import com.dev.services.models.ServiceRequest
import me.arunsharma.devupdates.databinding.ItemDataSourceBinding
import me.arunsharma.devupdates.utils.cache.FeedUtils

class VHDataSourceItem(val binding: ItemDataSourceBinding) : BaseViewHolder(binding.root)

class DataSourceAdapter(items: List<ServiceRequest>) :
    BaseRecyclerViewAdapter<ServiceRequest, VHDataSourceItem>(items.toMutableList()) {
    override fun convert(helper: VHDataSourceItem, item: ServiceRequest) {
        helper.binding.ivServiceLogo.setImageResource(FeedUtils.getDrawable(item.type))
        helper.binding.tvTitle.text = getTitle(item)
        helper.binding.tvSubTitle.text = item.name
    }

    private fun getTitle(item: ServiceRequest): String? {
        if (item.type == DataSource.GITHUB) {
            return item.metadata?.get("language")
        } else {
            return item.metadata?.get("username")
        }
    }

    override fun createHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return VHDataSourceItem(
            ItemDataSourceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}