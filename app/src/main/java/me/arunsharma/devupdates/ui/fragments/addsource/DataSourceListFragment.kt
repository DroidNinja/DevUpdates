package me.arunsharma.devupdates.ui.fragments.addsource

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.core.base.BaseFragment
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FragmentDataSourceBinding
import me.arunsharma.devupdates.utils.SnackbarUtil

@AndroidEntryPoint
class DataSourceListFragment : BaseFragment(R.layout.fragment_data_source) {

    private val binding by viewBinding(FragmentDataSourceBinding::bind)

    val viewModel: VMDataSource by activityViewModels()

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
            if (binding.recyclerView.adapter == null) {
                binding.recyclerView.adapter =
                    DataSourceAdapter(
                        listItems,
                        object : DataSourceAdapter.DataSourceAdapterListener {
                            override fun onDragComplete() {
                                val adapter = binding.recyclerView.adapter as? DataSourceAdapter
                                adapter?.let {
                                    viewModel.saveConfig(it.mData)
                                    SnackbarUtil.showBarShortTime(
                                        view = requireView(),
                                        message = getString(R.string.changes_saved)
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

            } else {
                (binding.recyclerView.adapter as? DataSourceAdapter)?.setData(listItems)
            }
        }

        viewModel.onDataSourceAdded.observe(viewLifecycleOwner) { message ->
            if (message != null) {
                SnackbarUtil.showBarShortTime(
                    view = requireView(),
                    message = message
                )
            }
        }

        binding.btnAddSource.setOnClickListener {
            showAddSource()
        }

        viewModel.getServices()
    }

    private fun showAddSource() {
        AddSourceFragment.newInstance()
            .show(requireActivity().supportFragmentManager, AddSourceFragment.TAG)
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
