package me.arunsharma.devupdates.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.viewmodels.VMFeed

class FeedFragment : BaseFragment() {

    private lateinit var viewModel: VMFeed

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    companion object {
        fun newInstance() = FeedFragment()
    }
}