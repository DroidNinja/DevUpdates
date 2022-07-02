package me.arunsharma.devupdates.ui.fragments.addsource

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.core.base.BaseFragment
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FragmentAddDataSourceBinding
import me.arunsharma.devupdates.utils.SnackbarUtil

@AndroidEntryPoint
class DataSourceListFragment : BaseFragment(R.layout.fragment_add_data_source) {

    private val binding by viewBinding(FragmentAddDataSourceBinding::bind)

    val viewModel: VMDataSource by viewModels()

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun initView() {
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
        }

        viewModel.lvFetchConfig.observe(viewLifecycleOwner) { listItems ->
            binding.recyclerView.adapter =
                DataSourceAdapter(listItems, object : DataSourceAdapter.DataSourceAdapterListener {
                    override fun onDragComplete() {
                        val adapter = binding.recyclerView.adapter as? DataSourceAdapter
                        adapter?.let {
                            viewModel.saveConfig(it.mData)
                            SnackbarUtil.showBarShortTime(
                                requireView(),
                                getString(R.string.changes_saved)
                            )
                        }
                    }
                }).apply {
                    val itemTouchHelper = RecyclerViewMoveHelper.create(this)
                    itemTouchHelper.attachToRecyclerView(binding.recyclerView)

                    setOnItemChildClickListener(object :
                        BaseRecyclerViewAdapter.OnItemChildClickListener {
                        override fun onItemChildClick(view: View, position: Int) {
                            when (view.id) {
                                R.id.btnDelete -> onDeleteItem(this@apply, position)
                            }
                        }
                    })
                }

        }

        viewModel.getServices()
    }

    private fun onDeleteItem(
        adapter: DataSourceAdapter,
        position: Int
    ) {
        viewModel.deleteSource(adapter.mData[position])
        adapter.removeItem(position)
    }

    companion object {
        const val TAG = "AddDataSourceFragment"
        fun newInstance(): DataSourceListFragment = DataSourceListFragment()
    }
}
