package com.example.allvets.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.allvets.ui.theme.avBorderSelected
import com.example.allvets.ui.theme.statusPending


@Composable
fun LoadingDialog(onDismiss: () -> Unit? = {}) {
    Dialog(
        onDismissRequest = { onDismiss.invoke() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .width(140.dp)
                .height(140.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .width(64.dp),
                color = statusPending,
                trackColor = avBorderSelected,
            )
        }
    }
}