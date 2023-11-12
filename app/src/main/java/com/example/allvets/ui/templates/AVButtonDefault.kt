package com.example.allvets.ui.templates

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.allvets.ui.theme.BackGroud
import com.example.allvets.ui.theme.BtnBlue

@Composable
fun ButtonDefault(
    modifier: Modifier = Modifier,
    textButton: String? = "textBtn",
    enabled: Boolean = true,
    radius: Dp = 0.dp,
    colorBackground: Color = BtnBlue,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (enabled) colorBackground else BackGroud,
            contentColor = MaterialTheme.colors.surface
        ), modifier = modifier, shape = RoundedCornerShape(radius)
    ) {
        Text(
            text = textButton ?: ""
        )
    }
}
