package com.example.allvets.ui.medicalRecord

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allvets.presentation.medicalRecord.AVMedicalRecordEvent
import com.example.allvets.presentation.medicalRecord.AVMedicalRecordVM
import com.example.allvets.ui.templates.*
import com.example.allvets.ui.theme.*
import com.example.allvets.utils.LoadingDialog
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
    var selectedItem by rememberSaveable { mutableStateOf("") }

    val state = vm.state

    LaunchedEffect(key1 = state.medicalRecordData == null, key2 = idPet) {
        vm.onEvent(AVMedicalRecordEvent.GetMedicalRecord(idUser, idPet))
    }

    LaunchedEffect(Unit) {
        snapshotFlow { sheetState.currentValue }
            .collect {
                if (it == ModalBottomSheetValue.Hidden) {
                    selectedItem = ""
                }
            }
    }

    AllVetsTheme {
        Scaffold(
            Modifier.fillMaxSize(),
            topBar = {
                AVTopBar(
                    titleScreen = "Expediente",
                    name = state.medicalRecordData?.patient?.firstOrNull()?.name ?: "",
                    onBack = onBack
                )
            }
        ) {
            if (state.loading) {
                LoadingDialog()
            }
            state.medicalRecordData?.let { listData ->
                ModalBottomSheetLayout(
                    sheetState = sheetState,
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    modifier = Modifier.padding(top = 0.dp),
                    sheetContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(backgroundAll)
                                .wrapContentWidth(unbounded = false)
                                .wrapContentHeight(unbounded = true)
                        ) {
                            HeaderBottomSheet()
                            Column(
                                modifier = Modifier
                                    .padding(top = 36.dp, bottom = 16.dp)
                                    .height(600.dp)
                                    .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AVMedicalRecordCommon(
                                    data = state.medicalRecordSelect.value,
                                )
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
                                ),
                                isSelected = selectedItem == it.id
                            ) {
                                selectedItem = it.id
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