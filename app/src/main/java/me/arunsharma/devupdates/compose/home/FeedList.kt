package me.arunsharma.devupdates.compose.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dev.services.models.ServiceItem

@Composable
fun FeedList(
    items: List<ServiceItem>,
    modifier: Modifier,
    onItemClick: (ServiceItem) -> Unit,
    onBookmarkClick: (ServiceItem) -> Unit
) {
    Scaffold(
        modifier = modifier
    ) {
        LazyColumn {
            items(
                count = items.size,
                itemContent = { index ->
                    FeedListItem(items[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClick(items[index])
                            }) {
                        onBookmarkClick(it)
                    }
                    Divider()
                }
            )
        }
    }
}


@Preview
@Composable
fun PreviewDemoUis() {
    FeedList(
        mutableListOf(
            ServiceItem(
                title = "Kotlin",
                actionUrl = "",
                description = "",
                author = "",
                likes = "",
                createdAt = 0L,
                isBookmarked = false,
                sourceType = "",
                groupId = "",
                topTitleText = ""
            )
        ),
        modifier = Modifier.fillMaxSize(),
        {

        },{})
}