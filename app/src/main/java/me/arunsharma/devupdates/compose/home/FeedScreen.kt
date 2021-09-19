package me.arunsharma.devupdates.compose.home


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.core.utils.CustomTabHelper
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import me.arunsharma.devupdates.compose.theme.ArimoFamily
import me.arunsharma.devupdates.compose.theme.getTabTextColorSelector
import me.arunsharma.devupdates.compose.utils.rememberFlowWithLifecycle
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import me.arunsharma.devupdates.ui.fragments.feed.adapter.FeedPagerItem
import me.arunsharma.devupdates.ui.fragments.feed.viewmodel.VMFeed
import timber.log.Timber

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedScreen(
    viewModel: VMFeed = hiltViewModel()
) {
    val config: List<FeedPagerItem> by rememberFlowWithLifecycle(viewModel.flowFetchConfig)
        .collectAsState(initial = mutableListOf())
    val pagerState = rememberPagerState(pageCount = config.size)
    FeedScreen(pagerState, config)
    Timber.d("Recomposition:FeedScreen")
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedScreen(
    pagerState: PagerState,
    config: List<FeedPagerItem>
) {
    if (config.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            FeedTab(
                pagerState, config
            )

            FeedPager(
                pagerState,
                config,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedTab(
    pagerState: PagerState,
    feedPagerItems: List<FeedPagerItem>
) {
    Timber.d("Recomposition:FeedTab")
    if (pagerState.pageCount == 0) return

    val coroutineScope = rememberCoroutineScope()

    ScrollableTabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.onPrimary,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
    ) {
        // Add tabs for all of our pages
        feedPagerItems.forEachIndexed { index, season ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    // Animate to the selected page when clicked
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            ) {
                CustomTab(
                    text =
                    feedPagerItems[index].request.name,
                    imageId = feedPagerItems[index].icon,
                    selected = pagerState.currentPage == index
                )
            }
        }
    }
}

@Composable
private fun CustomTab(
    text: String,
    imageId: Int,
    selected: Boolean,
    modifier: Modifier = Modifier.padding(10.dp)
) {
    Surface(
        modifier = modifier
    ) {
        Column(modifier = Modifier) {
            Icon(
                painter = painterResource(imageId),
                tint = getTabTextColorSelector(isSystemInDarkTheme(), selected),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = text,
                color = getTabTextColorSelector(isSystemInDarkTheme(), selected),
                fontFamily = ArimoFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedPager(
    pagerState: PagerState,
    feedPagerItems: List<FeedPagerItem>,
    modifier: Modifier
) {
    Timber.d("Recomposition:HomeFeedPagerViewItem")
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
    ) { page ->
        Timber.d("page:$page")
        if (page == 0) {
            HomeFeed(pagerState, feedPagerItems)
//            homeViewModel.observeHomeFeed(feedPagerItems[page].request)
        } else if (page == 1) {
            GithubFeed(pagerState, feedPagerItems)
//            feedListViewModel.getData(feedPagerItems[page].request)
        } else {
            GenericFeed(pagerState, feedPagerItems)
        }
    }
}


@Composable
fun FeedPagerViewItem(
    feedUIState: FeedUIState?,
    modifier: Modifier
) {
    Timber.d("Recomposition:FeedPagerViewItem" + feedUIState.toString())
    val context = LocalContext.current
    when (feedUIState) {
        is FeedUIState.Loading -> {
        }
        is FeedUIState.ShowList -> {
            Timber.d("size:" + feedUIState.list.size)
            FeedList(items = feedUIState.list, modifier) { item ->
                CustomTabHelper.open(context, item.actionUrl)
            }
        }
        is FeedUIState.ShowError -> {

        }
        is FeedUIState.HasNewItems -> {

        }
    }
}