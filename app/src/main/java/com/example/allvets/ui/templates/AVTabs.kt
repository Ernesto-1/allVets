package com.example.allvets.ui.templates

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.allvets.ui.theme.GreenLight

@Composable
fun AVTabs(
    options: Array<String>,
    tabSelected: String,
    onSelected: ((Int, String) -> Unit)? = null,
) {
    LazyRow(
        modifier = Modifier
            .wrapContentHeight().fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        itemsIndexed(options) { index, item ->
            AVItemTab(
                isSelected = tabSelected.uppercase() == item.uppercase(),
                textButton = item,
                onChecked = { tab ->
                    if (tabSelected != tab) {
                        onSelected?.invoke(index, tab)
                    }
                }
            )
        }
    }
}

@Composable
fun AVItemTab(
    isSelected: Boolean,
    tabColorSelected: Color = GreenLight,
    textButton: String,
    onChecked: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .wrapContentHeight()
            .clickable {
                onChecked(textButton)
            }
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 2.dp),
            text = textButton)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp),
            color = if (isSelected) tabColorSelected else Color.Transparent
        )
    }
}