package me.arunsharma.devupdates.ui.fragments.settings

import android.content.Context
import androidx.fragment.app.viewModels
import com.dev.core.base.BaseFragment
import com.dev.core.di.utils.DaggerInjectable
import com.dev.core.utils.viewBinding
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.databinding.FragmentSettingsBinding
import me.arunsharma.devupdates.ui.MainActivityListener
import me.arunsharma.devupdates.ui.fragments.addsource.AddDataSourceFragment


class SettingsFragment : BaseFragment(R.layout.fragment_settings), DaggerInjectable {

    private var mActivityListener: MainActivityListener? = null

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityListener) {
            mActivityListener = context
        } else {
            throw RuntimeException("$context must implement FCAxisFDActivityListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivityListener = null
    }

    override fun initView() {
        binding.btnChooseTheme.setOnClickListener {
            ThemeSettingDialogFragment.newInstance()
                .show(parentFragmentManager, null)
        }

        binding.btnAdjustDataSource.setOnClickListener {
            mActivityListener?.getNavigator()
                ?.openAddDataSourceFragment(activity, AddDataSourceFragment.newInstance())
        }
    }

    override fun injectDagger() {

    }

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}
