package com.example.allvets.ui.templates

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.allvets.R
import com.example.allvets.ui.theme.Orange
import com.example.allvets.ui.theme.plata
import java.util.*

@Composable
fun MyTime(): String{

    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]
    val mTime = remember { mutableStateOf("") }
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            val formattedHour = String.format("%02d", mHour)
            val formattedMinute = String.format("%02d", mMinute)
            mTime.value = "$formattedHour:$formattedMinute"
        }, mHour, mMinute, true
    )

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = mTime.value,
            onValueChange = { mTime.value = it },
            label = { Text("Selecciona la hora") },
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
                    mTimePickerDialog.show()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_timer),
                        contentDescription = "fecha", tint = Orange
                    )
                }
            },
            readOnly = true,
            enabled = false
        )
    }
    return mTime.value
}

