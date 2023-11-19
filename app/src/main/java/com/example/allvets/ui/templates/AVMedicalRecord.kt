package com.example.allvets.ui.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
        }
    }
}

@Composable
fun DataPatient(data: RecordData) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = data.medicalMatter,
            modifier = Modifier.fillMaxSize(),
            style = stTitle
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.date?.let { date ->
                ItemText(label = "Fecha ", valueLabel = convertTimestampToString(date))
                ItemText(label = "CP Medico ", valueLabel = data.license )
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
                Text(
                    text = it, modifier = Modifier.padding(16.dp)
                )
            }
        }

        diagnosisList?.let {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                diagnosisList.forEach { itemTreat ->
                    ContainerCard {
                        Text(
                            text = "$itemTreat",
                            modifier = Modifier.padding(16.dp)
                        )
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
                            numMedicine = medicine.numero_medicamento
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecordItem(type: String, name: String, numMedicine: String) {
    ContainerCard {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconRecord = if (type == AppConstans.SpeciesConstants.TYPE_MEDICINE)
                R.drawable.ic_medicine else R.drawable.ic_vaccine
            Image(
                painter = painterResource(
                    id = iconRecord
                ),
                contentDescription = "icon_vaccine",
                modifier = Modifier.height(50.dp),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                ItemText(label = "Tipo: ", valueLabel = type )
                ItemText(label = "Tratamiento: ", valueLabel =("$name $numMedicine"))
            }
        }
    }
}

@Composable
fun ContainerCard(content: @Composable () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .toStyleCard(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        content()
    }
}

@Composable
fun ItemText(label: String, valueLabel:String) {
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
            textAlign = TextAlign.Left
        )
    )
}

private fun Modifier.toStyleCard(): Modifier {
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