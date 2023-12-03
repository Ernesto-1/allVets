package com.example.allvets.ui.commons.dropdown

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.allvets.ui.theme.avGray

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AVDropDown(valueType: String = "Medicamento", onValueChange: (String) -> Unit = {}) {
    val options = listOf("Medicamento", "Vacuna")

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(valueType) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = !expanded
    }) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = selectedOptionText,
                onValueChange = { },
                readOnly = true,
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    disabledIndicatorColor = Color.White,
                    errorIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    backgroundColor = Color.White,
                    trailingIconColor = avGray,
                    disabledTrailingIconColor = avGray,
                    errorTrailingIconColor = avGray,
                    focusedTrailingIconColor = avGray
                )
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onValueChange.invoke(selectionOption)
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}