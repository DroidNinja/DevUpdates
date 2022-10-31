package me.arunsharma.devupdates.compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.compose.bookmarks.BookmarksScreen
import me.arunsharma.devupdates.compose.home.FeedScreen
import me.arunsharma.devupdates.compose.settings.SettingsScreen
import me.arunsharma.devupdates.compose.theme.DevUpdatesTheme
import timber.log.Timber

@Preview
@Composable
fun DevUpdatesApp() {
    DevUpdatesTheme {
        val homeScreenState = rememberSaveable { mutableStateOf(Screen.Feed.route) }

        Column {
            HomeScreenContent(
                homeScreen = homeScreenState.value,
                modifier = Modifier.weight(1f)
            )
            HomeBottomNavigation(
                selectedNavigation = homeScreenState.value,
                onNavigationSelected = { selected ->
                    homeScreenState.value = selected.route
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )
        }

    }
}

@Composable
fun HomeScreenContent(
    homeScreen: String,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Crossfade(homeScreen) { screen ->
            Surface(color = MaterialTheme.colors.background) {
                when (screen) {
                    Screen.Feed.route -> FeedScreen()
                    Screen.Bookmarks.route -> BookmarksScreen()
                    Screen.Settings.route -> SettingsScreen()

                }
            }
        }
    }
}

@Composable
internal fun HomeBottomNavigation(
    selectedNavigation: String,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = modifier
    ) {
        HomeBottomNavigationItem(
            label = stringResource(R.string.feed),
            selected = selectedNavigation == Screen.Feed.route,
            onClick = { onNavigationSelected(Screen.Feed) },
            contentDescription = stringResource(R.string.feed),
            painter = painterResource(R.drawable.ic_feed),
        )

        HomeBottomNavigationItem(
            label = stringResource(R.string.bookmarks),
            selected = selectedNavigation == Screen.Bookmarks.route,
            onClick = { onNavigationSelected(Screen.Bookmarks) },
            contentDescription = stringResource(R.string.bookmarks),
            painter = painterResource(R.drawable.ic_bookmarks),
        )
        HomeBottomNavigationItem(
            label = stringResource(R.string.settings),
            selected = selectedNavigation == Screen.Settings.route,
            onClick = { onNavigationSelected(Screen.Settings) },
            contentDescription = stringResource(R.string.settings),
            painter = painterResource(R.drawable.ic_settings),
        )
    }
}


@Composable
private fun RowScope.HomeBottomNavigationItem(
    selected: Boolean,
    painter: Painter,
    contentDescription: String,
    label: String,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        icon = {
            Icon(
                painter = painter,
                contentDescription = contentDescription,
                modifier = if (selected) Modifier.alpha(1f) else Modifier.alpha(0.8f)
            )
        },
        label = {
            Text(
                text = label,
                modifier = if (selected) Modifier.alpha(1f) else Modifier.alpha(0.8f)
            )
        },
        selected = selected,
        onClick = onClick,
    )
}
