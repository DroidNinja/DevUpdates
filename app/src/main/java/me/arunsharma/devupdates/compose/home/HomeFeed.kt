package me.arunsharma.devupdates.compose.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.dev.services.api.models.ServiceItem
import com.dev.services.api.models.ServiceRequest
import com.sebaslogen.resaca.hilt.hiltViewModelScoped
import me.arunsharma.devupdates.compose.HomeScreenViewModel
import me.arunsharma.devupdates.compose.utils.rememberFlowWithLifecycle
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import timber.log.Timber


@Composable
fun HomeFeed(request: ServiceRequest) {
    Timber.d("Recomposition:HomeFeed")
    val homeViewModel = hiltViewModelScoped<HomeScreenViewModel>()
//    CustomText(text = request.name)

    val feedUIState: FeedUIState by rememberFlowWithLifecycle(homeViewModel.state)
        .collectAsState(initial = FeedUIState.Loading)

    HomeFeed(feedUIState) { item ->
        homeViewModel.addBookmark(item)
    }

    LaunchedEffect(request.name) {
        Timber.d("LaunchedEffect" + request.name)
        if (feedUIState !is FeedUIState.ShowList) {
            homeViewModel.observeHomeFeed(request)
        }
    }
}

@Composable
fun HomeFeed(feedUIState: FeedUIState, onBookmarkClick: (ServiceItem) -> Unit) {

    FeedPagerViewItem(
        feedUIState = feedUIState,
        modifier = Modifier.fillMaxSize()
    ) {
        onBookmarkClick(it)
    }
}