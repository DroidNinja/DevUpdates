package me.arunsharma.devupdates.compose.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.dev.services.api.models.ServiceRequest
import com.sebaslogen.resaca.hilt.hiltViewModelScoped
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import me.arunsharma.devupdates.ui.fragments.feed.viewmodel.VMFeedList
import timber.log.Timber

@Composable
fun GithubFeed(request: ServiceRequest) {
    Timber.d("Recomposition:GithubFeed")
    val feedListViewModel = hiltViewModelScoped<VMFeedList>()
//    CustomText(text = request.name)
    val state: FeedUIState? by feedListViewModel.lvUiState.observeAsState()

    GithubFeed(state)

    LaunchedEffect(request.name) {
        feedListViewModel.getFeed(request)
    }
}

@Composable
fun GithubFeed(state: FeedUIState?) {

    FeedPagerViewItem(
        feedUIState = state,
        modifier = Modifier.fillMaxSize()
    ) { }
}