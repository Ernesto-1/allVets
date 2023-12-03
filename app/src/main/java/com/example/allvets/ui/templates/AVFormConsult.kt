package com.example.allvets.ui.templates

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.allvets.data.remote.model.CardMedicalDiagnosis
import com.example.allvets.ui.commons.buttons.AVButtonAdd
import com.example.allvets.ui.commons.calendar.AVCalendarCommon
import com.example.allvets.ui.commons.dropdown.AVDropDown
import com.example.allvets.ui.commons.form.AlignItemForm
import com.example.allvets.ui.commons.form.CheckboxItem
import com.example.allvets.ui.commons.form.ItemForm
import com.google.firebase.Timestamp
import java.util.*

@Composable
fun AVFormConsult(
    listCardMedical: List<CardMedicalDiagnosis>? = null,
    onAddItem: (CardMedicalDiagnosis) -> Unit = {}
) {
    val _showForm = remember { mutableStateOf(false) }
    val showForm: State<Boolean> = _showForm

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showForm.value) {
            FormAddItemVaccinateCard(
                onCancel = {
                    _showForm.value = false
                },
                onAddItem = {
                    onAddItem.invoke(it)
                    _showForm.value = false
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                listCardMedical?.let {
                    if (it.isNotEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            it.forEach { item ->
                                RecordItem(
                                    type = item.type,
                                    name = item.treatment,
                                    numMedicine = item.code,
                                    nextAplication = item.nextAplication
                                )
                            }
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        )
                    }
                }
            }

            AVButtonAdd(text = "Agregar registro") {
                _showForm.value = true
            }

        }
    }
}

@Composable
fun FormAddItemVaccinateCard(
    onAddItem: (CardMedicalDiagnosis) -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val _type = mutableStateOf("Medicamento")
    val type: MutableState<String> = _type

    val _treatment = mutableStateOf("")
    val treatment: MutableState<String> = _treatment

    val _treatmentError = mutableStateOf(false)
    val treatmentError: MutableState<Boolean> = _treatmentError

    val _code = mutableStateOf("")
    val code: MutableState<String> = _code

    val _singleApplication = mutableStateOf(false)
    val singleApplication: MutableState<Boolean> = _singleApplication

    val _dateSingleApplication = mutableStateOf("")
    val dateSingleApplication: MutableState<String> = _dateSingleApplication

    ContainerCard {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tipo:",
                        modifier = Modifier.width(110.dp)
                    )
                    AVDropDown(valueType = type.value) {
                        type.value = it
                    }
                }

                ItemForm(
                    valueItem = treatment.value,
                    label = "Tratamiento:",
                    isError = treatmentError.value
                ) {
                    if (it.isNotEmpty() && treatmentError.value) {
                        _treatmentError.value = false
                    }
                    treatment.value = it
                }

                ItemForm(
                    label = "Código de medicamento",
                    valueItem = code.value,
                    alignItemForm = AlignItemForm.VERTICAL
                ) {
                    code.value = it
                }

                CheckboxItem(label = "Aplicación unica") {
                    singleApplication.value = it
                }


                AVCalendarCommon(
                    date = dateSingleApplication,
                    isActive = !singleApplication.value
                ) {
                    dateSingleApplication.value = it
                }

                ButtonDefault(
                    textButton = "Agregar",
                    radius = 8.dp,
                    modifier = Modifier.padding(vertical = 6.dp)
                ) {
                    if (treatment.value.isEmpty()) {
                        _treatmentError.value = true
                    } else {
                        onAddItem.invoke(
                            CardMedicalDiagnosis(
                                type = type.value,
                                treatment = treatment.value,
                                code = code.value,
                                nextAplication = if (singleApplication.value) "" else dateSingleApplication.value,
                                date = Timestamp(Date(System.currentTimeMillis()))
                            )
                        )
                        _type.value = "Medicamento"
                        _code.value = ""
                        _treatment.value = ""
                        _dateSingleApplication.value = ""
                    }
                }
            }

            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "fecha",
                modifier = Modifier.clickable {
                    onCancel.invoke()
                }
            )
        }
    }
}