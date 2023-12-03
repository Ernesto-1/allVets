package com.example.allvets.ui.diagnosis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allvets.R
import com.example.allvets.data.remote.model.AVDiagnosisRequest
import com.example.allvets.presentation.diagnosis.AVDiagnosisEvent
import com.example.allvets.presentation.diagnosis.AVDiagnosisVM
import com.example.allvets.ui.templates.AVFormConsult
import com.example.allvets.ui.templates.AVFormVaccinationCard
import com.example.allvets.ui.templates.AVTabs
import com.example.allvets.ui.templates.AVTopBar
import com.example.allvets.ui.theme.AllVetsTheme
import com.example.allvets.utils.LoadingDialog
import com.google.firebase.Timestamp
import java.util.*

@Composable
fun AVDiagnosis(
    vm: AVDiagnosisVM = hiltViewModel(),
    idUser: String,
    idPet: String,
    namePet: String,
    medicalMatter: String,
    license: String,
    idDate: String,
    onBackRefresh: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var selectedTab by rememberSaveable { mutableStateOf("CARTILLA") }
    var currentTab by rememberSaveable { mutableStateOf("CARTILLA") }
    var onChangeTab by rememberSaveable { mutableStateOf(false) }

    val state = vm.state

    AllVetsTheme {
        Scaffold(
            Modifier.fillMaxSize(),
            topBar = {
                AVTopBar(
                    titleScreen = "Diagnostico",
                    name = namePet,
                    onBack = onBack
                )
            }
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (currentTab == "CONSULTA" && selectedTab == "CARTILLA") {
                    onChangeTab = true
                } else {
                    currentTab = selectedTab
                }
                if (state.loading) {
                    LoadingDialog()
                }
                if(state.saveSuccessfully){
                    onBackRefresh.invoke()
                }
                AVTabs(
                    options = stringArrayResource(id = R.array.options_diagnosis),
                    tabSelected = selectedTab,
                ) { _, tab ->
                    selectedTab = tab.uppercase()
                }
                when (currentTab) {
                    "CARTILLA" -> {
                        AVFormConsult(
                            listCardMedical = vm.itemList
                        ) {
                            vm.addItemCard(it)
                        }
                    }
                    "CONSULTA" -> {
                        AVFormVaccinationCard(
                            onChangeTab = onChangeTab,
                            tempData = vm.diagnosis.value,
                            onSaveTempData = {
                                vm.saveTempDiagnosis(it)
                                onChangeTab = false
                                currentTab = selectedTab
                            }
                        ) {
                            vm.onEvent(
                                AVDiagnosisEvent.SaveDiagnosis(
                                    AVDiagnosisRequest(
                                        idUser = idUser,
                                        idPet = idPet,
                                        medicalMatter = medicalMatter,
                                        license = license,
                                        cardMedical = vm.itemList,
                                        consultation = it,
                                        date = Timestamp(Date(System.currentTimeMillis())),
                                        idDate = idDate
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}