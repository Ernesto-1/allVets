package com.example.allvets.ui.templates

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.allvets.data.remote.model.MedicalConsultation
import com.example.allvets.ui.commons.buttons.AVButtonAdd
import com.example.allvets.ui.commons.form.CustomTextField

@Composable
fun AVFormVaccinationCard(
    onChangeTab: Boolean = false,
    tempData: MedicalConsultation? = null,
    onSaveTempData: (MedicalConsultation) -> Unit = {},
    onSendData: (MedicalConsultation) -> Unit = {}
) {
    val _comments = remember { mutableStateOf("") }
    val comments: MutableState<String> = _comments

    val _diagnosis = remember { mutableStateOf("") }
    val diagnosis: MutableState<String> = _diagnosis

    val _treatments = remember { mutableStateListOf<String>() }
    val treatments: List<String> = _treatments

    val treatmentsData = remember { mutableStateMapOf<String, String>() }

    if (tempData != null) {
        if (tempData.diagnosis?.isNotEmpty() == true && diagnosis.value.isEmpty()) {
            _diagnosis.value = tempData.diagnosis
        }
        if (tempData.comments?.isNotEmpty() == true && comments.value.isEmpty()) {
            _comments.value = tempData.comments
        }
        if (tempData.treaments.isNotEmpty() && treatments.isEmpty()) {
            tempData.treaments.map {
                _treatments.add(it.key)
                treatmentsData[it.key] = it.value
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (treatments.isEmpty()) {
            _treatments.add("0")
        }
    }

    if (onChangeTab) {
        onSaveTempData.invoke(
            MedicalConsultation(
                comments = comments.value,
                diagnosis = diagnosis.value,
                treaments = treatmentsData
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        AVItemForm(
            label = "Comentarios",
            valueItem = comments.value
        ) {
            comments.value = it
        }

        AVItemForm("Diagnostico", valueItem = diagnosis.value) {
            diagnosis.value = it
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            treatments.forEachIndexed { index, field ->
                AVItemForm(
                    label = if (index == 0) "Tratamiento" else "",
                    valueItem = treatmentsData[field] ?: "",
                    noItem = (index + 1),
                    heightItem = 55.dp
                ) {
                    treatmentsData[field] = it
                }
            }
        }

        AVButtonAdd(text = "Agregar tratamiento") {
            _treatments.add("${treatments.size}")
        }

        ButtonDefault(
            textButton = "Enviar",
            radius = 8.dp,
            modifier = Modifier.padding(vertical = 6.dp)
        ) {
            onSendData.invoke(
                MedicalConsultation(
                    comments = comments.value,
                    diagnosis = diagnosis.value,
                    treaments = treatmentsData
                )
            )
        }
    }
}

@Composable
fun AVItemForm(
    label: String,
    valueItem: String,
    noItem: Int = 0,
    heightItem: Dp = 110.dp,
    onValueChange: (String) -> Unit = {}
) {
    val _textValue = remember {
        mutableStateOf(valueItem)
    }
    val textValue: MutableState<String> = _textValue

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (label.isNotEmpty()) {
            Text(text = label)
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (noItem > 0) {
                Text(
                    text = "$noItem - ",
                    modifier = Modifier.padding(top = 4.dp, start = 8.dp, end = 8.dp)
                )
            }
            CustomTextField(
                heightItem = heightItem,
                valueItem = textValue.value,
            ) {
                textValue.value = it
                onValueChange.invoke(it)
            }
        }
    }
}