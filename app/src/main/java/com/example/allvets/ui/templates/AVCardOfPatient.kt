package com.example.allvets.ui.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allvets.R
import com.example.allvets.data.remote.model.Quotes
import com.example.allvets.ui.theme.Alertsuccess
import com.example.allvets.ui.theme.RedAlert
import com.example.allvets.ui.theme.greyText
import com.example.allvets.ui.theme.statusPending
import com.example.allvets.utils.AppConstans
import com.example.allvets.utils.capitalizeName
import com.example.allvets.utils.convertTimestampToString2


@Preview(showBackground = true)
@Composable
fun AVCardOfPatient(
    dataCard: Quotes = Quotes(), tabSelect: Int = 0, onClickPatient: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    onClickPatient.invoke()

                }
                )
                .padding(vertical = 8.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column() {
                Text(
                    text = if (tabSelect == 0) dataCard.status?.capitalizeName()
                        .toString() else dataCard.affairs.toString(),
                    modifier = Modifier.fillMaxWidth(),
                    color = if (tabSelect == 0) if (dataCard.status.toString() == "pendiente") statusPending else greyText else if (dataCard.affairs.toString() == "Consulta") Alertsuccess else RedAlert,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
                Row {
                    Image(
                        painter = painterResource(
                            id = if (tabSelect == 0) when (dataCard.pet) {
                                AppConstans.SpeciesConstants.DOG -> R.drawable.ic_dog_face
                                AppConstans.SpeciesConstants.CAT -> R.drawable.ic_cat
                                AppConstans.SpeciesConstants.BIRD -> R.drawable.ic_bird
                                AppConstans.SpeciesConstants.FISH -> R.drawable.ic_fish
                                else -> R.drawable.ic_dog_face
                            } else {
                                when (dataCard.pet) {
                                    AppConstans.SpeciesConstants.DOG -> R.drawable.ic_dog_vet
                                    AppConstans.SpeciesConstants.CAT -> R.drawable.ic_cat_vet
                                    AppConstans.SpeciesConstants.BIRD -> R.drawable.ic_bird_vet
                                    AppConstans.SpeciesConstants.FISH -> R.drawable.ic_fish_vet
                                    else -> R.drawable.ic_dog_vet
                                }
                            }
                        ),
                        contentDescription = "Image_patient",
                        modifier = Modifier.width(60.dp),
                        contentScale = ContentScale.FillWidth
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                        append("Paciente: ")
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                                        )
                                    ) {
                                        append(dataCard.patient)
                                    }
                                }, modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                            )
                        }
                        val datePet =
                            dataCard.requestedAppointment?.let { convertTimestampToString2(it) }
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                    append(if (dataCard.status == "pendiente") "Fecha solicitada: " else "Fecha propuesta: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append(datePet)
                                }
                            },
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
        }
    }
}