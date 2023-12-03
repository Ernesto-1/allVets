package com.example.allvets.ui.commons.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.allvets.ui.theme.avBlack

@Composable
fun AVButtonAdd(text: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(26.dp)
            .clickable { onClick.invoke() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.AddCircle,
            contentDescription = text,
            modifier = Modifier.height(20.dp),
            tint = avBlack
        )
        Text(
            text = text,
            color = avBlack,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}