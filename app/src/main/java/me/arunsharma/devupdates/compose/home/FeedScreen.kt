package me.arunsharma.devupdates.compose.home


import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.core.utils.CustomTabHelper
import com.dev.services.models.DataSource
import com.dev.services.models.ServiceItem
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import me.arunsharma.devupdates.compose.theme.ArimoFamily
import me.arunsharma.devupdates.compose.theme.PlaceHolder
import me.arunsharma.devupdates.compose.theme.getTabTextColorSelector
import me.arunsharma.devupdates.compose.utils.rememberFlowWithLifecycle
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState
import me.arunsharma.devupdates.ui.fragments.feed.adapter.FeedPagerItem
import me.arunsharma.devupdates.ui.fragments.feed.viewmodel.VMFeed
import me.arunsharma.devupdates.utils.LogCompositions
import me.vponomarenko.compose.shimmer.shimmer
import timber.log.Timber

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedScreen(
    viewModel: VMFeed = hiltViewModel()
) {
    Timber.d("Recomposition:FeedScreen")
    LogCompositions("TAG", "FeedScreen")
    val config: List<FeedPagerItem> by rememberFlowWithLifecycle(viewModel.flowFetchConfig)
        .collectAsState(initial = mutableListOf())

    if (config.isEmpty()) return

    val pages = mutableListOf<Page>()
    config.forEach { item ->
        when (item.request.type) {
            DataSource.ALL -> pages.add(Page(
                item
            ) {
                HomeFeed(item.request)
            })
            DataSource.GITHUB -> pages.add(Page(
                item
            ) {
                GithubFeed(item.request)
            })
            else -> pages.add(Page(
                item
            ) {
                GenericFeed(item.request)
            })
        }
    }

    PagesScreen(pages = listOf(
        Page(
            config[0]
        ) {
            HomeFeed(config[0].request)
        },
        Page(
            config[1]
        ) {
            GithubFeed(config[1].request)
        },
        Page(
            config[2]
        ) {
            GenericFeed(config[2].request)
        }
    ))
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagesScreen(
    modifier: Modifier = Modifier,
    pages: List<Page>
) {
    Timber.d("Recomposition:PagesScreen")
    val pagerState = rememberPagerState()
    Scaffold(
        modifier = modifier,
        topBar = {
            FeedTab(pagerState = pagerState, feedPagerItems = pages)
        }
    ) {
        HorizontalPager(
            modifier = Modifier.padding(it),
            count = pages.size,
            state = pagerState
        ) { page ->
            pages[page].content()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedTab(
    pagerState: PagerState,
    feedPagerItems: List<Page>
) {
    Timber.d("Recomposition:FeedTab")
    LogCompositions("TAG", "FeedTab" + pagerState.currentPage)
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
                    feedPagerItems[index].item.request.name,
                    imageId = feedPagerItems[index].item.icon,
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
    pages: List<Page>,
    modifier: Modifier,
//    content: @Composable PagerScope.(request: ServiceRequest) -> Unit,
) {
//    LogCompositions("TAG", "FeedPager")

}

@Composable
fun CustomText(text: String) {
    LogCompositions("TAG", "CustomText" + text)
    Text(text = text)
}


@Composable
fun FeedPagerViewItem(
    feedUIState: FeedUIState?,
    modifier: Modifier,
    onBookmarkClick: (ServiceItem) -> Unit?
) {
    val context = LocalContext.current
    when (feedUIState) {
        is FeedUIState.Loading -> {
            PlaceHolder(modifier.shimmer())
        }
        is FeedUIState.ShowList -> {
            Timber.d("size:" + feedUIState.list.size)
            FeedList(items = feedUIState.list, modifier, { item ->
                CustomTabHelper.open(context, item.actionUrl)
            }, {
                onBookmarkClick(it)
            })
        }
        is FeedUIState.ShowError -> {

        }
        is FeedUIState.HasNewItems -> {

        }
        else -> {}
    }
}
