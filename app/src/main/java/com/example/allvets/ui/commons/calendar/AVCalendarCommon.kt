package com.example.allvets.ui.commons.calendar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.allvets.ui.theme.Orange
import com.example.allvets.ui.theme.plata
import com.example.allvets.utils.datePicker
import java.util.*

@Composable
fun AVCalendarCommon(
    date: MutableState<String>,
    isActive: Boolean,
    onValueChangeDate: (String) -> Unit = {}
) {
    if (isActive) {
        val context = LocalContext.current
        val focusManager = LocalFocusManager.current

        val mCalendar = Calendar.getInstance()
        val mYear: Int = mCalendar.get(Calendar.YEAR)
        mCalendar.time = Date()

        val mDatePickerDialog =
            datePicker(date = date, context = context, focusManager = focusManager)
        mDatePickerDialog.datePicker.minDate = mCalendar.timeInMillis
        mCalendar.set(mYear + 1, 11, 31)
        mDatePickerDialog.datePicker.maxDate = mCalendar.timeInMillis

        OutlinedTextField(
            value = date.value,
            onValueChange = {
                onValueChangeDate.invoke(it)
            },
            label = { Text("Fecha próxima aplicación") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .width(282.dp)
                .padding(vertical = 4.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = plata,
                unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(
                    alpha = 0.15f
                ),
                textColor = Color.Black,
                focusedLabelColor = Color.Black
            ),
            trailingIcon = {
                IconButton(onClick = {
                    mDatePickerDialog.show()
                }) {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = "fecha", tint = Orange
                    )
                }
            },
            readOnly = true,
            enabled = false
        )
    }
}