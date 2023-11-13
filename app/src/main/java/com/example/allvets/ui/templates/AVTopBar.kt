package com.example.allvets.ui.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.allvets.R
import com.example.allvets.ui.theme.avGray
import com.example.allvets.ui.theme.stHeader

@Composable
fun AVTopBar(onBack: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = "back medical report",
            modifier = Modifier
                .height(26.dp)
                .clickable { onBack.invoke() },
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(avGray)
        )
        Text(text = "Expediente", style = stHeader)
    }
}