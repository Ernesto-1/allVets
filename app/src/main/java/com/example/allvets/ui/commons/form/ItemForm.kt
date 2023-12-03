package com.example.allvets.ui.commons.form

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ItemForm(
    label: String,
    valueItem: String,
    alignItemForm: AlignItemForm = AlignItemForm.HORIZONTAL,
    isError: Boolean? = false,
    onValueChange: (String) -> Unit = {}
) {
    when (alignItemForm) {
        AlignItemForm.HORIZONTAL -> {
            ContentItemFormHorizontal(
                label = label,
                valueItem = valueItem,
                isError = isError,
                onValueChange = onValueChange
            )
        }
        AlignItemForm.VERTICAL -> {
            ContentItemFormVertical(
                label = label,
                valueItem = valueItem,
                isError = isError,
                onValueChange = onValueChange
            )
        }
    }
}

@Composable
fun ContentItemFormHorizontal(
    label: String,
    valueItem: String,
    isError: Boolean? = false,
    onValueChange: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.width(110.dp))
        CustomTextField(
            valueItem = valueItem,
            isError = isError,
            onValueChange = onValueChange
        )
    }
}

@Composable
fun ContentItemFormVertical(
    label: String,
    valueItem: String,
    isError: Boolean? = false,
    onValueChange: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .width(250.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        CustomTextField(
            valueItem = valueItem,
            isError = isError,
            onValueChange = onValueChange
        )
        Text(text = label)
    }
}

enum class AlignItemForm {
    HORIZONTAL,
    VERTICAL
}