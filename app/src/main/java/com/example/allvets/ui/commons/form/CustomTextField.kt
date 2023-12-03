package com.example.allvets.ui.commons.form

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.allvets.R
import com.example.allvets.ui.theme.AVError
import com.example.allvets.ui.theme.BtnBlue
import com.example.allvets.ui.theme.avGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    heightItem: Dp = 40.dp,
    valueItem: String = "",
    isError: Boolean? = false,
    onValueChange: (String) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        BasicTextField(
            cursorBrush = SolidColor(BtnBlue),
            value = valueItem,
            onValueChange = {
                onValueChange.invoke(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = heightItem)
                .border(
                    border = BorderStroke(1.dp, if (isError == true) AVError else avGray),
                    shape = RoundedCornerShape(16.dp)
                )
                .background(color = Color.White),
            decorationBox = @Composable {
                OutlinedTextFieldDefaults.DecorationBox(
                    value = "",
                    innerTextField = it,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    trailingIcon = {
                        if (isError == true) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_error),
                                contentDescription = "error",
                                contentScale = ContentScale.FillBounds,
                                colorFilter = ColorFilter.tint(AVError)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(),
                    contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(start = 12.dp),
                    container = {},
                )
            }
        )
    }
}