package me.arunsharma.devupdates.ui.fragments.settings

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dev.core.di.utils.DaggerInjectable
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.ui.MainActivity
import javax.inject.Inject

enum class Theme(val storageKey: String) {
    LIGHT("light"),
    DARK("dark"),
    SYSTEM("system"),
    BATTERY_SAVER("battery_saver")
}

class ThemeSettingDialogFragment : AppCompatDialogFragment(), DaggerInjectable {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: VMSettings by viewModels {
        factory
    }

    private lateinit var listAdapter: ArrayAdapter<ThemeHolder>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        injectDagger()
        listAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_single_choice,
            viewModel.getAvailableThemes().map {
                ThemeHolder(it, getTitleForTheme(it))
            }
        )

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.choose_theme)
            .setSingleChoiceItems(listAdapter, 0) { dialog, position ->
                listAdapter.getItem(position)?.theme?.let {
                    viewModel.setTheme(it)
                }
                dialog.dismiss()
            }
            .create()
    }

    override fun onStart() {
        super.onStart()
        updateSelectedItem(viewModel.getTheme())
    }

    private fun updateSelectedItem(selected: Theme?) {
        val selectedPosition = (0 until listAdapter.count).indexOfFirst { index ->
            listAdapter.getItem(index)?.theme == selected
        }
        (dialog as? AlertDialog)?.listView?.setItemChecked(selectedPosition, true)
    }

    private fun getTitleForTheme(theme: Theme) = when (theme) {
        Theme.LIGHT -> getString(R.string.light)
        Theme.DARK -> getString(R.string.dark)
        Theme.SYSTEM -> getString(R.string.system)
        Theme.BATTERY_SAVER -> getString(R.string.battery_saver)
    }

    private data class ThemeHolder(val theme: Theme, val title: String) {
        override fun toString(): String = title
    }

    override fun injectDagger() {
        (activity as MainActivity).mainComponent.inject(this)
    }

    companion object {
        fun newInstance() = ThemeSettingDialogFragment()
    }
}