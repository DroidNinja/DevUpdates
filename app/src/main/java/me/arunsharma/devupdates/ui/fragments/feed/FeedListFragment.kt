package me.arunsharma.devupdates.ui.fragments.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.core.base.BaseFragment
import com.dev.core.di.utils.DaggerInjectable
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.utils.CustomTabHelper
import com.dev.core.utils.viewBinding
import com.dev.services.models.ServiceRequest
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FragmentFeedListBinding
import me.arunsharma.devupdates.ui.MainActivity
import me.arunsharma.devupdates.ui.viewmodels.VMFeed
import javax.inject.Inject

class FeedListFragment : BaseFragment(R.layout.fragment_feed_list), DaggerInjectable {

    private val binding by viewBinding(FragmentFeedListBinding::bind)

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    val viewModel: VMFeed by viewModels {
        factory
    }

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDagger()
    }

    override fun initView() {
        arguments?.getParcelable<ServiceRequest>(EXTRA_SERVICE_REQUEST)?.let { request->
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                setHasFixedSize(true)
            }
            viewModel.lvUiState.observe(viewLifecycleOwner, { state ->
                handleUIState(state)
            })

            viewModel.getData(request)
        }
    }

    private fun handleUIState(state: VMFeed.FeedUIState?) {
        when(state){
            is VMFeed.FeedUIState.Loading -> {
                binding.progressLayout.showLoading(true, arrayOf(R.layout.skeleton_item_feed_item))
            }
            is VMFeed.FeedUIState.ShowList -> {
                binding.recyclerView.adapter = FeedAdapter(state.list).apply {
                    setOnItemClickListener(object: BaseRecyclerViewAdapter.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            CustomTabHelper.open(view.context, getItem(position).actionUrl)
                        }
                    })
                }
                binding.progressLayout.showContent()
            }
        }
    }

    companion object {
        const val TAG = "FeedListFragment"
        const val EXTRA_SERVICE_REQUEST = "EXTRA_SERVICE_REQUEST"
        fun newInstance(request: ServiceRequest): FeedListFragment = FeedListFragment().apply {
            arguments  =Bundle().apply {
                putParcelable(EXTRA_SERVICE_REQUEST, request)
            }
        }
    }

    override fun injectDagger() {
        (activity as MainActivity).mainComponent.inject(this)
    }
}