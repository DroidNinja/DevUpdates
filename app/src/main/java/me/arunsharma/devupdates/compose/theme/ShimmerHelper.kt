package me.arunsharma.devupdates.compose.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun PlaceHolder(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Column {
            repeat(10) {
                FeedLoaderPlaceHolder(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun FeedLoaderPlaceHolder(modifier: Modifier) {
    Column(modifier = modifier.padding(15.dp)) {
        LinePlaceHolder(modifier = Modifier.width(100.dp).height(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        LinePlaceHolder(modifier = Modifier.fillMaxWidth().height(20.dp))
        Spacer(modifier = Modifier.height(10.dp))
        LinePlaceHolder(modifier = Modifier.width(100.dp).height(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        LinePlaceHolder(modifier = Modifier.width(50.dp).height(10.dp))
    }
}

@Composable
fun LinePlaceHolder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFFCDC9C9),
                shape = RoundedCornerShape(4.dp)
            )
    )
}