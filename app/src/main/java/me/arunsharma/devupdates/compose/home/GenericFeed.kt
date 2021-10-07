package me.arunsharma.devupdates.compose.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.services.models.ServiceItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.flow.collect
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import me.arunsharma.devupdates.ui.fragments.feed.adapter.FeedPagerItem
import me.arunsharma.devupdates.ui.fragments.feed.viewmodel.VMFeedList
import timber.log.Timber


@OptIn(ExperimentalPagerApi::class)
@Composable
fun GenericFeed(pagerState: PagerState, feedPagerItems: List<FeedPagerItem>) {
    Timber.d("Recomposition:GithubFeed")
    val feedListViewModel = hiltViewModel<VMFeedList>()

    GenericFeed(viewModel = feedListViewModel) { item->
        feedListViewModel.addBookmark(item)
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            Timber.d("changedPage:$page")
            feedListViewModel.getFeed(feedPagerItems[page].request)
        }
    }
}

@Composable
fun GenericFeed(viewModel: VMFeedList, onBookmarkClick: (ServiceItem) -> Unit) {
    Timber.d("Recomposition:GenericFeed")
    val state: FeedUIState? by viewModel.lvUiState.observeAsState()

    FeedPagerViewItem(
        feedUIState = state,
        modifier = Modifier.fillMaxSize(),
        onBookmarkClick
    )
}