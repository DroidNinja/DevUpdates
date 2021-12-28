package me.arunsharma.devupdates.ui.fragments.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.core.base.BaseFragment
import com.dev.core.databinding.LayoutProgressErrorBinding
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.recyclerview.RequestLoadMoreListener
import com.dev.core.utils.CustomTabHelper
import com.dev.core.utils.viewBinding
import com.dev.services.models.ServiceItem
import com.dev.services.models.ServiceRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FragmentHomeFeedListBinding
import me.arunsharma.devupdates.ui.fragments.feed.adapter.FeedAdapter
import me.arunsharma.devupdates.ui.fragments.feed.viewmodel.VMHomeFeed
import me.arunsharma.devupdates.utils.BookmarkEvent
import me.arunsharma.devupdates.utils.EventBus
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFeedFragment : BaseFragment(R.layout.fragment_home_feed_list) {

    private val binding by viewBinding(FragmentHomeFeedListBinding::bind)

    val viewModel: VMHomeFeed by viewModels()

    @Inject
    lateinit var eventBus: EventBus

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
                viewModel.getHomeFeed(request, forceUpdate = true)
            }

            binding.btnCheckUpdate.setOnClickListener {
                binding.btnCheckUpdate.visibility = View.GONE
                binding.srlView.isRefreshing = true
                viewModel.getHomeFeed(request, forceUpdate = true)
            }

            viewModel.lvUiState.observe(viewLifecycleOwner) { state ->
                handleUIState(state)
            }

            lifecycleScope.launchWhenStarted {
                eventBus.observe().collect { data ->
                    if (data is BookmarkEvent) {
                        (binding.recyclerView.adapter as? FeedAdapter)?.updateItem(data.item)
                    }
                }
            }

            arguments?.getParcelable<ServiceRequest>(EXTRA_SERVICE_REQUEST)
                ?.let { request ->
                    viewModel.feedItems.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                        .onEach { data ->
                            Timber.d("onNew Data:" + data.size)
                            viewModel.onDataReceived(data, request)
                        }
                        .launchIn(lifecycleScope)
                }
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
            is FeedUIState.HasNewItems -> {
                binding.btnCheckUpdate.visibility = View.VISIBLE
                binding.btnCheckUpdate.animate()
                    .translationY(binding.btnCheckUpdate.height.toFloat())
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
                            viewModel.updateHomeFeedData(mData, request)
                        }
                    }, binding.recyclerView)
                }
            }
        } else {
            val adapter = binding.recyclerView.adapter as FeedAdapter
                if (request.hasPagingSupport && request.next != null) {
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
        const val TAG = "HomeFeedFragment"
        const val EXTRA_SERVICE_REQUEST = "EXTRA_SERVICE_REQUEST"
        fun newInstance(request: ServiceRequest): HomeFeedFragment = HomeFeedFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_SERVICE_REQUEST, request)
            }
        }
    }
}