package me.arunsharma.devupdates.compose.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.services.models.ServiceItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import me.arunsharma.devupdates.compose.HomeScreenViewModel
import me.arunsharma.devupdates.compose.utils.rememberFlowWithLifecycle
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import me.arunsharma.devupdates.ui.fragments.feed.adapter.FeedPagerItem
import timber.log.Timber


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeFeed(pagerState: PagerState, feedPagerItems: List<FeedPagerItem>) {
    val homeViewModel = hiltViewModel<HomeScreenViewModel>()

    HomeFeed(homeViewModel.state) { item->
        homeViewModel.addBookmark(item)
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            Timber.d("changedPage:$page")
            if (homeViewModel.state.value !is FeedUIState.ShowList) {
                homeViewModel.observeHomeFeed(feedPagerItems[page].request)
            }
        }
    }
}

@Composable
fun HomeFeed(item1: StateFlow<FeedUIState>, onBookmarkClick: (ServiceItem) -> Unit) {
    Timber.d("Recomposition:HomeFeed")
    val feedUIState: FeedUIState by rememberFlowWithLifecycle(item1)
        .collectAsState(initial = FeedUIState.Loading)

    FeedPagerViewItem(
        feedUIState = feedUIState,
        modifier = Modifier.fillMaxSize()
    ) {
        onBookmarkClick(it)
    }
}