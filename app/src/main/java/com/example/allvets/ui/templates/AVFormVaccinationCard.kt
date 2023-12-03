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

    var _treatments = remember { mutableStateListOf<String>() }

    val treatments: List<String> = _treatments


    if (tempData != null) {
        if (tempData.diagnosis?.isNotEmpty() == true && diagnosis.value.isEmpty()) {
            _diagnosis.value = tempData.diagnosis
        }
        if (tempData.comments?.isNotEmpty() == true && comments.value.isEmpty()) {
            _comments.value = tempData.comments
        }
        if (tempData.treaments?.isNotEmpty() == true && treatments.isEmpty()) {
            tempData.treaments.forEach {
                _treatments.add(it)
            }
        }
    }



    LaunchedEffect(key1 = Unit) {
        if (_treatments.size == 0) {
            _treatments.add("")
        }
    }

    if (onChangeTab) {
        onSaveTempData.invoke(
            MedicalConsultation(
                comments = comments.value,
                diagnosis = diagnosis.value,
                treaments = treatments
            )
        )
    }


    val _treatmentItem = mutableStateOf("")
    val treatmentItem: MutableState<String> = _treatmentItem

    val _treatmentItemPos = mutableStateOf(0)
    val treatmentItemPos: MutableState<Int> = _treatmentItemPos

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
            treatments.forEachIndexed { index, treatment ->
                AVItemForm(
                    label = if (index == 0) "Tratamiento" else "",
                    valueItem = treatment,
                    noItem = (index + 1),
                    heightItem = 55.dp
                ) {
                    treatmentItem.value = it
                    treatmentItemPos.value = index
                }
            }
        }

        AVButtonAdd(text = "Agregar tratamiento") {
            _treatments.add(treatmentItemPos.value, treatmentItem.value)
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
                    treaments = treatments
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