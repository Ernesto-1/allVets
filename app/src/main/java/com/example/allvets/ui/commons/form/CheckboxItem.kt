package com.example.allvets.ui.commons.form

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.allvets.ui.theme.BtnBlue
import com.example.allvets.ui.theme.GreenLight

@Composable
fun CheckboxItem(
    label: String,
    onCheck: (Boolean) -> Unit = {}
) {

    val checked = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.height(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = {
                checked.value = it
                onCheck.invoke(it)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = GreenLight,
                uncheckedColor = BtnBlue,
                checkmarkColor = Color.White
            )
        )
        Text(
            text = label,
            style = TextStyle(color = BtnBlue, fontWeight = FontWeight.SemiBold)
        )
    }
}