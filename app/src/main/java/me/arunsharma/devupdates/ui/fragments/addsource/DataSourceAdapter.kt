package me.arunsharma.devupdates.ui.fragments.addsource

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.recyclerview.BaseViewHolder
import com.dev.services.models.DataSource
import com.dev.services.models.ServiceRequest
import me.arunsharma.devupdates.databinding.ItemDataSourceBinding
import me.arunsharma.devupdates.utils.cache.FeedUtils

class VHDataSourceItem(val binding: ItemDataSourceBinding) : BaseViewHolder(binding.root)

class DataSourceAdapter(items: List<ServiceRequest>, val mListener: DataSourceAdapterListener) :
    BaseRecyclerViewAdapter<ServiceRequest, VHDataSourceItem>(items.toMutableList()),
    RecyclerViewMoveHelper.ItemTouchHelperContract {

    interface DataSourceAdapterListener {
        fun onDragComplete()
    }

    override fun convert(helper: VHDataSourceItem, item: ServiceRequest) {
        helper.binding.ivServiceLogo.setImageResource(FeedUtils.getDrawable(item.type))
        helper.binding.tvTitle.text = item.name
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

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        val data = mData.removeAt(fromPosition)
        mData.add(toPosition, data)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(viewHolder: RecyclerView.ViewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onRowClear(viewHolder: RecyclerView.ViewHolder) {
        viewHolder.itemView.setBackgroundColor(Color.WHITE)
        mListener.onDragComplete()
    }

}