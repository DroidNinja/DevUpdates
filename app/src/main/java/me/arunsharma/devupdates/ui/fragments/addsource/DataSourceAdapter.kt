package me.arunsharma.devupdates.ui.fragments.addsource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.recyclerview.BaseViewHolder
import com.dev.services.models.ServiceRequest
import me.arunsharma.devupdates.R
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
        helper.addOnClickListener(R.id.btnDelete)
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
        viewHolder.itemView.setBackgroundColor(
            ContextCompat.getColor(
                viewHolder.itemView.context,
                R.color.colorOnPrimary_80
            )
        )
    }

    override fun onRowClear(viewHolder: RecyclerView.ViewHolder) {
        viewHolder.itemView.setBackgroundColor(
            ContextCompat.getColor(
                viewHolder.itemView.context,
                R.color.colorOnPrimary
            )
        )
        mListener.onDragComplete()
    }

    fun removeItem(position: Int) {
        mData.removeAt(position)
        notifyItemRemoved(position)
    }

}