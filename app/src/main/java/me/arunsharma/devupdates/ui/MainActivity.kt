package me.arunsharma.devupdates.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.core.extensions.addFragment
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.app.DevUpdatesApp
import me.arunsharma.devupdates.databinding.ActivityMainBinding
import me.arunsharma.devupdates.di.activity.MainComponent
import me.arunsharma.devupdates.ui.fragments.FeedFragment

class MainActivity : AppCompatActivity() {

    lateinit var mainComponent: MainComponent

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mainComponent = (applicationContext as DevUpdatesApp)
            .appComponent.mainComponent().create()
        mainComponent.inject(this)


        if (savedInstanceState == null) {
            addFragment(
                FeedFragment.newInstance(),
                R.id.fragment_container, false)
        }
    }
}
