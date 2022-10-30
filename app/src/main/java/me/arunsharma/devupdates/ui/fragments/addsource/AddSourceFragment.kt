package me.arunsharma.devupdates.ui.fragments.addsource

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.core.base.BaseFragment
import com.dev.core.recyclerview.BaseRecyclerViewAdapter
import com.dev.core.utils.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FragmentAddDataSourceBinding
import me.arunsharma.devupdates.databinding.FragmentDataSourceBinding
import me.arunsharma.devupdates.utils.SnackbarUtil

@AndroidEntryPoint
class AddSourceFragment : BottomSheetDialogFragment() {

    private val binding by viewBinding(FragmentAddDataSourceBinding::bind)

    val viewModel: VMAddSource by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_data_source, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddSource.setOnClickListener {
            viewModel.addSource(binding.chipGroupSource.checkedChipId, binding.etName.text.toString(), binding.etFeedUrl.text.toString())
            dismiss()
        }

        viewModel.lvError.observe(viewLifecycleOwner) { exception ->
            exception.message?.let { message ->
                SnackbarUtil.showBarShortTime(view, message)
            }
        }
    }

    companion object {
        val TAG = "AddSourceFragment"
        fun newInstance() = AddSourceFragment()
    }
}