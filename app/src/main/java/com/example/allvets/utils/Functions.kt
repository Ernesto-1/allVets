package com.example.allvets.utils

import androidx.compose.ui.graphics.Color
import com.example.allvets.R
import com.example.allvets.ui.theme.Alertsuccess
import com.example.allvets.ui.theme.RedAlert
import com.example.allvets.ui.theme.greyText
import com.example.allvets.ui.theme.statusPending

fun getColorStatus(tab: Int, status: String?, affairs: String?): Color {
    return when (tab) {
        0 -> {
            when (status) {
                "pendiente" -> {
                    statusPending
                }
                else -> {
                    greyText
                }
            }
        }
        else -> {
            if (status == "completada") {
                greyText
            } else {
                when (affairs) {
                    "Consulta" -> Alertsuccess
                    else -> {
                        RedAlert
                    }
                }
            }
        }
    }
}

fun getIconPet(tab: Int, pet: String? = ""): Int {
    return when (tab) {
        0 -> {
            when (pet) {
                AppConstans.SpeciesConstants.DOG -> R.drawable.ic_dog_face
                AppConstans.SpeciesConstants.CAT -> R.drawable.ic_cat
                AppConstans.SpeciesConstants.BIRD -> R.drawable.ic_bird
                AppConstans.SpeciesConstants.FISH -> R.drawable.ic_fish
                else -> R.drawable.ic_dog_face
            }
        }
        else -> {
            when (pet) {
                AppConstans.SpeciesConstants.DOG -> R.drawable.ic_dog_vet
                AppConstans.SpeciesConstants.CAT -> R.drawable.ic_cat_vet
                AppConstans.SpeciesConstants.BIRD -> R.drawable.ic_bird_vet
                AppConstans.SpeciesConstants.FISH -> R.drawable.ic_fish_vet
                else -> R.drawable.ic_dog_vet
            }
        }
    }
}