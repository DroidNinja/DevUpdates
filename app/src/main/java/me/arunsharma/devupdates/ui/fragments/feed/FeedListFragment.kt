package me.arunsharma.devupdates.ui.fragments.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.core.base.BaseFragment
import com.dev.core.databinding.LayoutProgressErrorBinding
import com.dev.core.di.utils.DaggerInjectable
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.recyclerview.RequestLoadMoreListener
import com.dev.core.utils.CustomTabHelper
import com.dev.core.utils.viewBinding
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import dagger.hilt.android.AndroidEntryPoint
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FragmentFeedListBinding
import me.arunsharma.devupdates.ui.MainActivity
import me.arunsharma.devupdates.ui.viewmodels.VMFeedList
import me.arunsharma.devupdates.utils.SnackbarUtil
import javax.inject.Inject

@AndroidEntryPoint
class FeedListFragment : BaseFragment(R.layout.fragment_feed_list) {

    private val binding by viewBinding(FragmentFeedListBinding::bind)

    val viewModel: VMFeedList by viewModels()

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun initView() {
        arguments?.getParcelable<ServiceRequest>(EXTRA_SERVICE_REQUEST)?.let { request ->
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                setHasFixedSize(true)
            }

            binding.srlView.setOnRefreshListener {
                viewModel.getData(request, forceUpdate = true)
            }

            viewModel.lvShowMessage.observe(viewLifecycleOwner, { resourceString ->
                view?.let { SnackbarUtil.showBarShortTime(it, getString(resourceString)) }
            })

            viewModel.lvUiState.observe(viewLifecycleOwner, { state ->
                handleUIState(state)
            })

            loadData()
        }
    }

    private fun handleUIState(state: FeedUIState?) {
        when (state) {
            is FeedUIState.Loading -> {
                binding.progressLayout.showLoading(true, arrayOf(R.layout.skeleton_item_feed_item))
            }
            is FeedUIState.ShowList -> {
                binding.srlView.isRefreshing = false
                binding.progressLayout.showContent()
                val data = state.list
                setDataOnList(state.request, data)
            }
            is FeedUIState.ShowError -> {
                LayoutProgressErrorBinding.inflate(LayoutInflater.from(requireContext())).apply {
                    tvTitle.text = state.errorTitle
                    tvSubtitle.text = state.errorSubtitle

                    binding.progressLayout.showError(root)
                }
            }
        }
    }

    private fun setDataOnList(request: ServiceRequest, data: List<ServiceItem>) =
        if (binding.recyclerView.adapter == null) {
            binding.recyclerView.adapter = FeedAdapter(data).apply {
                setOnItemClickListener(object :
                    BaseRecyclerViewAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        CustomTabHelper.open(view.context, getItem(position).actionUrl)
                    }
                })
                setOnItemChildClickListener(object :
                    BaseRecyclerViewAdapter.OnItemChildClickListener {
                    override fun onItemChildClick(view: View, position: Int) {
                        if (view.id == R.id.ivBookmark) {
                            val item = getItem(position)
                            item.isBookmarked = !item.isBookmarked
                            notifyItemChanged(position)
                            viewModel.addBookmark(item)
                        }
                    }
                })
                if (request.hasPagingSupport) {
                    setOnLoadMoreListener(object : RequestLoadMoreListener {
                        override fun onLoadMoreRequested() {
                            viewModel.updateData(mData, request)
                        }
                    }, binding.recyclerView)
                }
            }
        } else {
            val adapter = binding.recyclerView.adapter as FeedAdapter
            if (request.hasPagingSupport) {
                if (data.isNotEmpty()) {
                        adapter.addData(data)
                    adapter.loadMoreComplete()
                } else {
                    adapter.loadMoreEnd()
                }
            } else {
                adapter.setData(data)
            }
        }

    companion object {
        const val TAG = "FeedListFragment"
        const val EXTRA_SERVICE_REQUEST = "EXTRA_SERVICE_REQUEST"
        fun newInstance(request: ServiceRequest): FeedListFragment = FeedListFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_SERVICE_REQUEST, request)
            }
        }
    }

    private fun loadData() {
        arguments?.getParcelable<ServiceRequest>(EXTRA_SERVICE_REQUEST)?.let { request ->
            if (view != null && viewModel.lvUiState.value == null) {
                viewModel.getData(request)
            }
        }
    }
}