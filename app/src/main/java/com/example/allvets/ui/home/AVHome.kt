package com.example.allvets.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AVHome(navController: NavController){
    Column() {
        Text(text = "Hola soy el inicio")
    }
}