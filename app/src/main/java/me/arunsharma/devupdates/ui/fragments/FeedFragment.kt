package me.arunsharma.devupdates.ui.fragments

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dev.core.base.BaseFragment
import com.dev.core.di.utils.DaggerInjectable
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
        viewModel.getData()
    }

    companion object {
        const val TAG = "FeedFragment"
        fun newInstance(): FeedFragment = FeedFragment()
    }

    override fun injectDagger() {
        (activity as MainActivity).mainComponent.inject(this)
    }
}