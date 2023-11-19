package com.example.allvets.ui.templates

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.allvets.R
import com.example.allvets.ui.theme.avBorderSelected
import com.example.allvets.ui.theme.avGrayBorder

@Preview(showBackground = true)
@Composable
fun VetCard(
    vetInfo: VetCardInfo = VetCardInfo(
        date = "2023-11-09",
        reason = "Consulta",
        vetLicense = "Ced.Prof.: 12345"
    ),
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
        border = BorderStroke(
            if (isSelected) 2.dp else 1.dp,
            if (isSelected) avBorderSelected else avGrayBorder
        )

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                    Text(text = vetInfo.date, fontWeight = FontWeight.Bold)
                }

                Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = vetInfo.reason, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_medical),
                    contentDescription = null,
                    tint = Color.Gray
                )
                Text(text = vetInfo.vetLicense, fontWeight = FontWeight.Bold)
            }
        }
    }
}

data class VetCardInfo(
    val date: String,
    val reason: String,
    val vetLicense: String
)