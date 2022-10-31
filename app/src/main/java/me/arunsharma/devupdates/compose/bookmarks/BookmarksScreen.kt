package me.arunsharma.devupdates.compose.bookmarks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.compose.home.FeedPagerViewItem
import me.arunsharma.devupdates.ui.fragments.bookmarks.VMBookmarks
import me.arunsharma.devupdates.ui.fragments.feed.FeedUIState

@Composable
fun BookmarksScreen(viewModel: VMBookmarks = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.bookmarks),
                    color = MaterialTheme.colors.primary
                )
            },
            elevation = 2.dp
        )
        BookmarksFeed(viewModel = viewModel)
    }

    viewModel.getBookmarks()
}

@Composable
fun BookmarksFeed(viewModel: VMBookmarks) {
    val state: FeedUIState? by viewModel.lvUiState.observeAsState()

    FeedPagerViewItem(
        feedUIState = state,
        modifier = Modifier.fillMaxSize()
    ) {
        viewModel.removeBookmark(it)
    }
}