package com.example.allvets.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusManager
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToString2(timestamp: Timestamp): String {
    val date = timestamp.toDate()
    val dateFormat = SimpleDateFormat("d 'de' MMMM 'del' yyyy 'a las' HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}

fun convertTimestampToString(timestamp: Timestamp): String {
    val date = timestamp.toDate()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

fun datePicker(date: MutableState<String>, context: Context, focusManager: FocusManager): DatePickerDialog {
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    return DatePickerDialog(
        context, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val formattedMonth = String.format("%02d", mMonth + 1) // Formatear el mes con dos dígitos
            date.value = "$mDayOfMonth/$formattedMonth/$mYear"
            focusManager.clearFocus()
        }, mYear, mMonth, mDay
    )
}

fun convertDateTimeToTimestamp(dateString: String, timeString: String): Timestamp? {
    val dateTimeString = "$dateString $timeString"
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return try {
        val date = dateFormat.parse(dateTimeString)
        Timestamp(Date(date?.time ?: (20 / 10 / 2022)))
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun String.capitalizeName(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
}