package com.example.allvets.ui.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allvets.R
import com.example.allvets.data.remote.model.RecordData
import com.example.allvets.ui.theme.avGrayBorder
import com.example.allvets.ui.theme.avGrayShadow
import com.example.allvets.ui.theme.stText
import com.example.allvets.ui.theme.stTitle
import com.example.allvets.utils.AppConstans
import com.example.allvets.utils.capitalizeName
import com.example.allvets.utils.convertTimestampToString

@Composable
fun AVMedicalRecordCommon(data: RecordData, recordData: List<RecordData>) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        data.let { record ->

            DataPatient(data = record)

            RecordDataItem(title = "Comentarios", diagnosis = record.comments)

            RecordDataItem(title = "Diagnostico", diagnosis = record.diagnosis)

            RecordDataItem(title = "Tratamiento", diagnosisList = record.treatment)

            RecordList(data = recordData)

            Divider(
                modifier = Modifier.height(8.dp),
                color = Color.Transparent
            )
        }
    }
}

@Composable
fun DataPatient(data: RecordData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = data.medicalMatter,
            modifier = Modifier.fillMaxWidth(),
            style = stTitle
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            data.date?.let { date ->
                ItemText(
                    modifier = Modifier.weight(1f),
                    label = "Fecha ",
                    valueLabel = convertTimestampToString(date)
                )
                ItemText(
                    modifier = Modifier.weight(1f),
                    label = "CP Medico ",
                    valueLabel = data.license
                )
            }
        }
    }
}

@Composable
fun RecordDataItem(title: String, diagnosis: String? = null, diagnosisList: ArrayList<*>? = null) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = stTitle.copy(fontSize = 14.sp)
        )
        diagnosis?.let {
            ContainerCard {
                Text(text = it)
            }
        }

        diagnosisList?.let {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                diagnosisList.forEachIndexed { index, itemTreat ->
                    if (itemTreat.toString().isNotEmpty()) {
                        ContainerCard {
                            Text(
                                text = "${index + 1}- $itemTreat"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecordList(data: List<RecordData>? = null) {
    data?.let { listVaccine ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Cartilla",
                style = stTitle.copy(fontSize = 14.sp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listVaccine.forEach { itemVaccine ->
                    itemVaccine.medicalRecordList.forEach { medicine ->
                        RecordItem(
                            type = medicine.tipo,
                            name = medicine.nombre,
                            numMedicine = medicine.numero_medicamento,
                            nextAplication = ""
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecordItem(
    type: String,
    name: String,
    numMedicine: String,
    nextAplication: String
) {
    ContainerCard {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconRecord = if (type.lowercase() == AppConstans.SpeciesConstants.TYPE_MEDICINE)
                R.drawable.ic_medicine else R.drawable.ic_vaccine
            Image(
                painter = painterResource(
                    id = iconRecord
                ),
                contentDescription = "icon_vaccine",
                modifier = Modifier.width(50.dp),
                contentScale = ContentScale.FillWidth
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                ItemText(label = "Tipo: ", valueLabel = type)
                ItemText(label = "Tratamiento: ", valueLabel = name)
                ItemText(
                    modifier = Modifier.padding(end = 50.dp),
                    label = "",
                    valueLabel = numMedicine,
                    align = TextAlign.Center
                )
                ItemText(label = "Próxima aplicación: ", valueLabel = nextAplication)
            }
        }
    }
}

@Composable
fun ContainerCard(content: @Composable () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .toStyleCard(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            content()
        }
    }
}

@Composable
fun ItemText(
    modifier: Modifier = Modifier,
    label: String,
    valueLabel: String,
    align: TextAlign = TextAlign.Left
) {
    if (valueLabel.isNotEmpty()) {
        Text(
            text = buildAnnotatedString {
                append(label)
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(valueLabel.capitalizeName())
                }
            },
            style = stText.copy(
                textAlign = align
            ),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

fun Modifier.toStyleCard(): Modifier {
    return this
        .shadow(
            elevation = 4.dp,
            spotColor = avGrayShadow,
            ambientColor = avGrayShadow
        )
        .border(
            width = 1.dp,
            color = avGrayBorder,
            shape = RoundedCornerShape(size = 10.dp)
        )
}