package me.arunsharma.devupdates.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.core.base.BaseFragment
import com.dev.core.di.utils.DaggerInjectable
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.utils.viewBinding
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FeedFragmentBinding
import me.arunsharma.devupdates.ui.MainActivity
import me.arunsharma.devupdates.ui.viewmodels.VMFeed
import javax.inject.Inject

class FeedFragment : BaseFragment(R.layout.feed_fragment), DaggerInjectable {

    private val binding by viewBinding(FeedFragmentBinding::bind)

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
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
        }
        viewModel.lvUiState.observe(viewLifecycleOwner, { state->
            handleUIState(state)
        })

        viewModel.getData()
    }

    private fun handleUIState(state: VMFeed.FeedUIState?) {
        when(state){
            is VMFeed.FeedUIState.Loading -> {

            }
            is VMFeed.FeedUIState.ShowList -> {
                binding.recyclerView.adapter = FeedAdapter(state.list).apply {
                    setOnItemClickListener(object: BaseRecyclerViewAdapter.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getItem(position).actionUrl))
                            startActivity(intent)
                        }
                    })
                }
            }
        }
    }

    companion object {
        const val TAG = "FeedFragment"
        fun newInstance(): FeedFragment = FeedFragment()
    }

    override fun injectDagger() {
        (activity as MainActivity).mainComponent.inject(this)
    }
}