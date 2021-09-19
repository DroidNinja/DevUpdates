package me.arunsharma.devupdates.compose.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.dev.services.models.ServiceItem
import me.arunsharma.devupdates.R
import me.arunsharma.devupdates.compose.theme.AppTypography

@Composable
fun FeedListItem(
    serviceItem: ServiceItem,
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier,
    ) {
        // Create references for the composables to constrain
        val (refBookmark) = createRefs()

        Column(modifier = Modifier.padding(15.dp)) {
            Text(
                text = serviceItem.topTitleText ?: "",
                style = AppTypography.body2
            )
            Text(
                text = serviceItem.title,
                style = AppTypography.h3,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = serviceItem.description ?: "",
                style = AppTypography.body2,
                modifier = Modifier.padding(top = 5.dp)
            )
            Text(
                text = serviceItem.likes ?: "",
                style = AppTypography.body2,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Icon(
            painter = painterResource(R.drawable.ic_to_bookmark_unselected),
            tint = MaterialTheme.colors.primary,
            contentDescription = stringResource(
                id = R.string.bookmarks
            ),
            modifier = Modifier.constrainAs(refBookmark) {
                top.linkTo(parent.top)
                end.linkTo(parent.end, margin = 10.dp)
            }
        )
    }

}