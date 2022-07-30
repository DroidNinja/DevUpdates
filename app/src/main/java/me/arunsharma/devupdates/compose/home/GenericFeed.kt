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
import com.dev.services.models.ServiceRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.sebaslogen.resaca.hilt.hiltViewModelScoped
import kotlinx.coroutines.flow.collect
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import me.arunsharma.devupdates.ui.fragments.feed.adapter.FeedPagerItem
import me.arunsharma.devupdates.ui.fragments.feed.viewmodel.VMFeedList
import timber.log.Timber


@Composable
fun GenericFeed(request: ServiceRequest) {
    Timber.d("Recomposition:GenericFeed")
    val feedListViewModel = hiltViewModelScoped<VMFeedList>()
//    CustomText(text = request.name)
    val state: FeedUIState? by feedListViewModel.lvUiState.observeAsState()
    GenericFeed(state = state) { item->
        feedListViewModel.addBookmark(item)
    }

    LaunchedEffect(request.name) {
        feedListViewModel.getFeed(request)
    }
}

@Composable
fun GenericFeed(state: FeedUIState?, onBookmarkClick: (ServiceItem) -> Unit) {

    FeedPagerViewItem(
        feedUIState = state,
        modifier = Modifier.fillMaxSize(),
        onBookmarkClick
    )
}