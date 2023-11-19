package com.example.allvets.ui.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.allvets.R
import com.example.allvets.ui.theme.avGray
import com.example.allvets.ui.theme.stHeader
import com.example.allvets.utils.capitalizeName

@Composable
fun AVTopBar(name: String, onBack: () -> Unit = {}) {
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
        ItemTextHeader(label = "Expediente", valueLabel = name)
    }
}

@Composable
fun ItemTextHeader(label: String, valueLabel: String) {
    Text(
        text = buildAnnotatedString {
            append("$label ")
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF5FA5C4),
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                append(valueLabel.capitalizeName())
            }
        },
        style = stHeader
    )
}