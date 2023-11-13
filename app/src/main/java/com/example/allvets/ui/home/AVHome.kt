package com.example.allvets.ui.home

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.allvets.R
import com.example.allvets.presentation.home.AVHomeEvent
import com.example.allvets.presentation.home.AVHomeViewModel
import com.example.allvets.presentation.home.SendInfoDate
import com.example.allvets.ui.navigation.Route
import com.example.allvets.ui.templates.*
import com.example.allvets.ui.theme.*
import com.example.allvets.utils.convertDateTimeToTimestamp
import com.example.allvets.utils.convertTimestampToString2
import com.example.allvets.utils.datePicker
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AVHome(navController: NavController, viewModel: AVHomeViewModel = hiltViewModel()) {
    val date = rememberSaveable { mutableStateOf("") }
    val state = viewModel.state
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserId", Context.MODE_PRIVATE)
    val myUserId = sharedPreferences.getString("myUserId", "")
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var isChecked by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val mYear: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mCalendar.time = Date()
    val mDatePickerDialog = datePicker(date = date, context = context, focusManager = focusManager)
    val timeSelect = remember { mutableStateOf("") }
    var selectedTabCoupons by rememberSaveable { mutableStateOf("Solicitudes") }


    LaunchedEffect(Unit) {
        if (myUserId?.isNotEmpty() == true) {
            viewModel.onEvent(AVHomeEvent.GetMyUser(myUserId))
        }
    }

    LaunchedEffect(state.dataUser, state.isSendDate == true) {
        if (state.dataUser?.consult?.isNotEmpty() == true || state.isSendDate == true) {
            viewModel.onEvent(AVHomeEvent.GetAllQuotes(state.dataUser?.consult.toString()))
            state.isSendDate = false
        }
    }

    LaunchedEffect(state.tabSelected.value, state.dataAllQuotes) {
        viewModel.onEvent(
            AVHomeEvent.FilterQuotes(
                state.tabSelected.value,
                idVet = myUserId.toString()
            )
        )
    }

    Log.i("TAG_vets", "AVHome: ${state.dataQuotesSelected}")

    mDatePickerDialog.datePicker.minDate = mCalendar.timeInMillis
    mCalendar.set(mYear + 1, 11, 31)
    mDatePickerDialog.datePicker.maxDate = mCalendar.timeInMillis
    ModalBottomSheetLayout(sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier.padding(0.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(backgroundAll)
                    .wrapContentWidth(unbounded = false)
                    .wrapContentHeight(unbounded = true)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    HeaderBottomSheet()
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = state.dataQuotesSelected.value.patient.toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = BackGroud
                        )
                        Text(
                            text = state.dataQuotesSelected.value.age.toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                    append("Asunto: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append(state.dataQuotesSelected.value.affairs)
                                }
                            },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                    append("Motivo: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append(state.dataQuotesSelected.value.reason)
                                }
                            },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        if (state.dataQuotesSelected.value.requestedAppointment != null) {
                            val datePet =
                                convertTimestampToString2(state.dataQuotesSelected.value.requestedAppointment as Timestamp)
                            Text(text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                    append(if (state.dataQuotesSelected.value.status == "pendiente") "Fecha solicitada: " else "Fecha propuesta: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append(datePet)
                                }
                            }, modifier = Modifier.padding(vertical = 8.dp))
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (state.tabSelected.value == 0) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Checkbox(
                                        checked = isChecked,
                                        onCheckedChange = { newCheckedState ->
                                            isChecked = newCheckedState
                                        },
                                    )
                                    Text(
                                        text = "Ofrecer cambio de fecha",
                                        modifier = Modifier.padding(vertical = 12.dp)
                                    )
                                }
                                if (isChecked) {
                                    OutlinedTextField(
                                        value = date.value,
                                        onValueChange = { date.value = it },
                                        label = { Text("Fecha de la cita") },
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
                                    timeSelect.value = MyTime()
                                } else {
                                    timeSelect.value = ""

                                }
                                ButtonDefault(
                                    textButton = if (isChecked) "Enviar" else "Confirmar",
                                    radius = 8.dp, modifier = Modifier.padding(vertical = 12.dp)
                                ) {
                                    state.dataAllQuotes = mutableListOf()
                                    val timestamp = convertDateTimeToTimestamp(
                                        dateString = date.value,
                                        timeString = timeSelect.value
                                    )
                                    val dataSend = SendInfoDate(
                                        idVet = myUserId.toString(),
                                        idDate = state.dataQuotesSelected.value.id.toString(),
                                        isConfirmed = isChecked,
                                        status = if (isChecked) "en espera" else "confirmada",
                                        requestedAppointment = timestamp
                                    )
                                    viewModel.onEvent(AVHomeEvent.SendDate(dataSend))
                                    scope.launch {
                                        sheetState.hide()
                                    }
                                }
                            } else {
                                isChecked = false
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 45.dp, bottom = 25.dp)
                                        .height(20.dp),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(modifier = Modifier.clickable {
                                        navController.navigate("${Route.AVMEDICAL_RECORD}/${state.dataQuotesSelected.value.userId}/${state.dataQuotesSelected.value.idPatient}")
                                    }) {
                                        Text(
                                            text = stringResource(id = R.string.label_show_medical_record),
                                            color = GreenLight,
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                        Icon(
                                            painter = painterResource(id = R.drawable.visibility_true),
                                            contentDescription = "",
                                            tint = BackGroud
                                        )
                                    }
                                    Row(modifier = Modifier.clickable {

                                    }) {
                                        Text(
                                            text = stringResource(id = R.string.label_add_medical_diagnosis),
                                            color = BtnBlue,
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                        Icon(
                                            imageVector = Icons.Filled.AddCircle,
                                            contentDescription = "",
                                            tint = Alertsuccess
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundAll),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hola soy el inicio")
            AVTabs(
                options = stringArrayResource(id = R.array.taps_optoins_coupons),
                tabSelected = selectedTabCoupons,
            ) { index, tab ->
                selectedTabCoupons = tab.uppercase()
                state.tabSelected.value = index
                state.dataFilterQuotes = mutableListOf()
            }
            LazyColumn {
                items(state.dataFilterQuotes) {
                    AVCardOfPatient(dataCard = it, tabSelect = state.tabSelected.value) {
                        date.value = ""
                        timeSelect.value = ""
                        isChecked = false
                        scope.launch {
                            state.dataQuotesSelected.value = it
                            sheetState.show()
                        }
                    }
                }
            }

        }
    }
}