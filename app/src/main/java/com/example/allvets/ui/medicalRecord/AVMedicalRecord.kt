package com.example.allvets.ui.medicalRecord

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allvets.R
import com.example.allvets.presentation.medicalRecord.AVMedicalRecordEvent
import com.example.allvets.presentation.medicalRecord.AVMedicalRecordVM
import com.example.allvets.ui.templates.AVTopBar
import com.example.allvets.ui.templates.HeaderBottomSheet
import com.example.allvets.ui.templates.VetCard
import com.example.allvets.ui.templates.VetCardInfo
import com.example.allvets.ui.theme.*
import com.example.allvets.utils.convertTimestampToString
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AVMedicalRecord(
    vm: AVMedicalRecordVM = hiltViewModel(),
    idUser: String,
    idPet: String,
    onBack: () -> Unit = {}
) {

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val state = vm.state

    Log.i("TAG_vet", "AVMedicalRecord: $idUser")
    Log.i("TAG_vet", "AVMedicalRecord: $idPet")

    LaunchedEffect(key1 = state.medicalRecordData == null, key2 = idPet) {
        vm.onEvent(AVMedicalRecordEvent.GetMedicalRecord(idUser, idPet))
    }

    AllVetsTheme {
        Scaffold(
            Modifier.fillMaxSize(),
            topBar = { AVTopBar(onBack = onBack) }
        ) {
            state.medicalRecordData?.let { listData ->
                ModalBottomSheetLayout(
                    sheetState = sheetState,
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    modifier = Modifier.padding(top = 0.dp), sheetContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(backgroundAll)
                                .wrapContentWidth(unbounded = false)
                                .wrapContentHeight(unbounded = true)
                        ) {
                            Log.i("TAG_record", "AVMedicalRecord: ${state.medicalRecordSelect}")
                            Column(
                                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                HeaderBottomSheet()
                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(24.dp)
                                ) {
                                    state.medicalRecordSelect.value.let { record ->
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Text(
                                                text = record.medicalMatter,
                                                modifier = Modifier.fillMaxSize(),
                                                style = stTitle
                                            )
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                record.date?.let { date ->
                                                    Text(
                                                        text = buildAnnotatedString {
                                                            append("Fecha ")
                                                            withStyle(
                                                                style = SpanStyle(fontWeight = FontWeight.SemiBold)
                                                            ) {
                                                                append(
                                                                    convertTimestampToString(
                                                                        date
                                                                    )
                                                                )
                                                            }
                                                        },
                                                        style = stText
                                                    )
                                                }
                                                Text(
                                                    text = buildAnnotatedString {
                                                        append("CP Medico ")
                                                        withStyle(
                                                            style = SpanStyle(fontWeight = FontWeight.SemiBold)
                                                        ) {
                                                            append(record.license)
                                                        }
                                                    },
                                                    style = stText
                                                )
                                            }
                                        }

                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Text(
                                                text = "Comentarios",
                                                style = stTitle.copy(fontSize = 14.sp)
                                            )
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .shadow(
                                                        elevation = 4.dp,
                                                        spotColor = Color(0x40000000),
                                                        ambientColor = Color(0x40000000)
                                                    )
                                                    .border(
                                                        width = 1.dp,
                                                        color = Color(0x1A000000),
                                                        shape = RoundedCornerShape(size = 10.dp)
                                                    ),
                                                colors = CardDefaults.cardColors(containerColor = Color.White)
                                            ) {
                                                Text(
                                                    text = record.comments, modifier = Modifier
                                                        .padding(16.dp)
                                                )
                                            }
                                        }

                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Text(
                                                text = "Diagnostico",
                                                style = stTitle.copy(fontSize = 14.sp)
                                            )
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .shadow(
                                                        elevation = 4.dp,
                                                        spotColor = Color(0x40000000),
                                                        ambientColor = Color(0x40000000)
                                                    )
                                                    .border(
                                                        width = 1.dp,
                                                        color = Color(0x1A000000),
                                                        shape = RoundedCornerShape(size = 10.dp)
                                                    ),
                                                colors = CardDefaults.cardColors(containerColor = Color.White)
                                            ) {
                                                Text(
                                                    text = record.diagnosis, modifier = Modifier
                                                        .padding(16.dp)
                                                )
                                            }
                                        }

                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Text(
                                                text = "Tratamiento",
                                                style = stTitle.copy(fontSize = 14.sp)
                                            )
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                record.treatment?.forEach { itemTreat ->
                                                    Card(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .shadow(
                                                                elevation = 4.dp,
                                                                spotColor = Color(0x40000000),
                                                                ambientColor = Color(0x40000000)
                                                            )
                                                            .border(
                                                                width = 1.dp,
                                                                color = Color(0x1A000000),
                                                                shape = RoundedCornerShape(size = 10.dp)
                                                            ),
                                                        colors = CardDefaults.cardColors(
                                                            containerColor = Color.White
                                                        )
                                                    ) {
                                                        Text(
                                                            text = "$itemTreat",
                                                            modifier = Modifier
                                                                .padding(16.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }

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


                                                state.medicalRecordData.record.let { listVaccine ->
                                                    listVaccine.forEach { itemVaccine ->
                                                        itemVaccine.medicalRecordList.forEach { medicine ->
                                                            Card(
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .shadow(
                                                                        elevation = 4.dp,
                                                                        spotColor = Color(
                                                                            0x40000000
                                                                        ),
                                                                        ambientColor = Color(
                                                                            0x40000000
                                                                        )
                                                                    )
                                                                    .border(
                                                                        width = 1.dp,
                                                                        color = Color(0x1A000000),
                                                                        shape = RoundedCornerShape(
                                                                            size = 10.dp
                                                                        )
                                                                    ),
                                                                colors = CardDefaults.cardColors(
                                                                    containerColor = Color.White
                                                                )
                                                            ) {
                                                                Row(
                                                                    modifier = Modifier
                                                                        .padding(12.dp)
                                                                        .fillMaxSize(),
                                                                    verticalAlignment = Alignment.CenterVertically
                                                                ) {
                                                                    Image(
                                                                        painter = painterResource(
                                                                            id = R.drawable.ic_medical
                                                                        ),
                                                                        contentDescription = "icon_vaccine",
                                                                        modifier = Modifier.height(
                                                                            50.dp
                                                                        ),
                                                                        contentScale = ContentScale.FillHeight
                                                                    )
                                                                    Column(
                                                                        modifier = Modifier
                                                                            .padding(start = 8.dp)
                                                                    ) {
                                                                        Text(
                                                                            text = buildAnnotatedString {
                                                                                append("Tipo: ")
                                                                                withStyle(
                                                                                    style = SpanStyle(
                                                                                        fontWeight = FontWeight.SemiBold
                                                                                    )
                                                                                ) {
                                                                                    append(
                                                                                        medicine.tipo
                                                                                    )
                                                                                }
                                                                            },
                                                                            style = stText,
                                                                            modifier = Modifier
                                                                                .padding(bottom = 4.dp)
                                                                        )
                                                                        Text(
                                                                            text = buildAnnotatedString {
                                                                                append("Tratamiento: ")
                                                                                withStyle(
                                                                                    style = SpanStyle(
                                                                                        fontWeight = FontWeight.SemiBold
                                                                                    )
                                                                                ) {
                                                                                    append(
                                                                                        medicine.nombre + " " + medicine.numero_medicamento
                                                                                    )
                                                                                }
                                                                            },
                                                                            style = stText.copy(
                                                                                textAlign = TextAlign.Left
                                                                            )
                                                                        )
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            /*Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 24.dp),
                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            record.medicine?.forEach { itemMedicine ->
                                                Log.i(
                                                    "TAG_vaccine",
                                                    "AVMedicalRecord: medicamente $itemMedicine"
                                                )
                                                Card(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .shadow(
                                                            elevation = 4.dp,
                                                            spotColor = Color(0x40000000),
                                                            ambientColor = Color(0x40000000)
                                                        )
                                                        .border(
                                                            width = 1.dp,
                                                            color = Color(0x1A000000),
                                                            shape = RoundedCornerShape(size = 10.dp)
                                                        ),
                                                    colors = CardDefaults.cardColors(
                                                        containerColor = Color.White
                                                    )
                                                ) {
                                                    Text(
                                                        text = "$itemMedicine",
                                                        modifier = Modifier
                                                            .padding(16.dp)
                                                    )
                                                }
                                            }
                                        }*/
                                        }
                                    }
                                }
                            }
                        }
                    }
                ) {
                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .padding(top = 56.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(listData.record) {
                            VetCard(
                                VetCardInfo(
                                    date = convertTimestampToString(it.date as Timestamp),
                                    reason = it.medicalMatter,
                                    vetLicense = "Ced.Prof. ${it.license}"
                                )
                            ) {
                                state.medicalRecordSelect.value = it
                                scope.launch {
                                    sheetState.show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}