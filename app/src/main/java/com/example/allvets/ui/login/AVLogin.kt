package com.example.allvets.ui.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.allvets.R
import com.example.allvets.presentation.login.AVLoginEvent
import com.example.allvets.presentation.login.AVLoginViewModel
import com.example.allvets.ui.navigation.Route
import com.example.allvets.ui.theme.*

@Composable
fun AVLogin(navController: NavController, viewModel: AVLoginViewModel = hiltViewModel()) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var hidden by rememberSaveable { mutableStateOf(true) }
    var errorEmail by rememberSaveable { mutableStateOf(false) }
    var errorPassword by rememberSaveable { mutableStateOf(false) }
    val state = viewModel.state
    val emailRegex = Regex("[A-Za-z\\d._%+-]+@[A-Za-z\\d.-]+\\.[A-Za-z]{2,4}")
    val focusRequester = remember { FocusRequester() }
    val screenWidth = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = (screenWidth * 0.50f)
    val sharedPreferences = context.getSharedPreferences("UserId", Context.MODE_PRIVATE)
    LaunchedEffect(state.isUserAutenticate) {
        if (state.isUserAutenticate?.isEmailVerified == true){
            navController.navigate(Route.AVHOME){
                launchSingleTop = true
                popUpTo(Route.AVLOGIN) {
                    inclusive = true
                }
            }
            Log.d("UserId",  state.isUserAutenticate?.uid.toString())
            sharedPreferences.edit()
                .putString(
                    "myUserId",
                    state.isUserAutenticate?.uid.toString()
                ).apply()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_allvets),
                contentDescription = "img_login",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight),
                contentScale = ContentScale.FillHeight
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = email,
                    onValueChange = {newEmail ->

                        if (newEmail.isEmpty() || newEmail.all { it.isLetterOrDigit() || it == '.' || it == '-' || it == '@' || it == '_'}) {
                            email = newEmail
                            errorEmail = false
                            state.message = ""
                        }
                    },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 2.5.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusRequester.requestFocus()
                        }
                    ),
                    textStyle = MaterialTheme.typography.body1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = plata,
                        unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                        textColor = Color.Black, focusedLabelColor = GreenLight
                    ), isError = errorEmail || state.message.isNotEmpty()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { pass ->
                        password = pass
                        errorPassword = false
                        state.message = ""
                    },
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 2.5.dp)
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ), shape = RoundedCornerShape(16.dp),
                    trailingIcon = {
                        if (password != "") {
                            IconButton(onClick = { hidden = !hidden }) {
                                val vector = painterResource(if (hidden) R.drawable.visibility_true else R.drawable.visibility_false)
                                val description = if (hidden) "Ocultar contraseña" else "Revelar contraseña"
                                Icon(painter = vector, contentDescription = description)
                            }
                        }
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                             viewModel.onEvent(AVLoginEvent.Login(email, password))
                        }
                    ),
                    singleLine = true,
                    visualTransformation = if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
                    textStyle = MaterialTheme.typography.body1,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = plata,
                        unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.15f),
                        textColor = Color.Black, focusedLabelColor = GreenLight
                    ), isError = errorPassword || state.message.isNotEmpty()
                )

                if (state.loading){
                    CircularProgressIndicator(color = GreenLight, modifier = Modifier
                        .size(30.dp)
                        .padding(vertical = 10.dp))
                }else if (errorEmail || state.message.isNotEmpty() || errorPassword) {
                    Text(
                        text =  state.message.ifEmpty { "Campo obligatorio" },
                        color = RedAlert,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }

                Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ButtonDefault(
                        textButton = "Iniciar sesion", modifier = Modifier.wrapContentWidth(), radius = 16.dp, enabled = !state.loading
                    ) {
                        if (email.isNotEmpty() && password.isNotEmpty() && email.matches(emailRegex)){
                            viewModel.onEvent(AVLoginEvent.Login(email, password))
                        }else{
                            errorEmail = true
                            errorPassword = true
                            if (!email.matches(emailRegex)){
                                state.message = "Nombre de usuario o contraseña no validos"
                            }
                        }
                    }
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            .clickable {}
                            .padding(top = 5.dp))

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Text(
                            text = "¿No tienes cuenta? Registrate",
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .clickable {

                                }
                                .padding(bottom = 16.dp)
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun ButtonDefault(
    modifier: Modifier = Modifier,
    textButton: String? = "textBtn",
    enabled: Boolean = true,
    radius: Dp = 0.dp,
    colorBackground: Color = BtnBlue,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (enabled)colorBackground else BackGroud,
            contentColor = MaterialTheme.colors.surface
        ),modifier = modifier, shape = RoundedCornerShape(radius)
    ) {
        Text(
            text = textButton ?: ""
        )
    }
}