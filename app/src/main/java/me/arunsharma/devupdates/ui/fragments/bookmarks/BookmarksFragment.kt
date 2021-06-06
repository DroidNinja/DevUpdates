package me.arunsharma.devupdates.ui.fragments.bookmarks

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.core.base.BaseFragment
import com.dev.core.databinding.LayoutProgressErrorBinding
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.utils.CustomTabHelper
import com.dev.core.utils.viewBinding
import com.dev.services.models.ServiceItem
import dagger.hilt.android.AndroidEntryPoint
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FragmentBookmarksBinding
import me.arunsharma.devupdates.ui.fragments.feed.adapter.FeedAdapter
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import me.arunsharma.devupdates.utils.SnackbarUtil

@AndroidEntryPoint
class BookmarksFragment : BaseFragment(R.layout.fragment_bookmarks) {

    private val binding by viewBinding(FragmentBookmarksBinding::bind)

    val viewModel: VMBookmarks by viewModels()

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun initView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
        }

        viewModel.lvUiState.observe(viewLifecycleOwner, {
            handleUIState(it)
        })

        viewModel.lvShowMessage.observe(viewLifecycleOwner, { resourceString ->
            view?.let { SnackbarUtil.showBarShortTime(it, getString(resourceString)) }
        })

        viewModel.getBookmarks()

        binding.srlView.setOnRefreshListener {
            viewModel.getBookmarks(forceUpdate = true)
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
                setDataOnList(data)
            }
            is FeedUIState.ShowError -> {
                binding.srlView.isRefreshing = false
                LayoutProgressErrorBinding.inflate(LayoutInflater.from(requireContext())).apply {
                    tvTitle.text = state.errorTitle
                    tvSubtitle.text = state.errorSubtitle

                    binding.progressLayout.showError(root)
                }
            }
        }
    }

    private fun setDataOnList(data: List<ServiceItem>) {
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
                        mData.removeAt(position)
                        notifyItemRemoved(position)
                        viewModel.removeBookmark(item)
                    }
                }
            })
        }
    }

    companion object {
        const val TAG = "BookmarksFragment"
        fun newInstance(): BookmarksFragment = BookmarksFragment()
    }
}
