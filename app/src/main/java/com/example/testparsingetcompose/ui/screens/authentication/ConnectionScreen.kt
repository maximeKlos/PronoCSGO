package com.example.testparsingetcompose.ui.screens.authentication

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testparsingetcompose.ui.navigation.AppMainScreens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import testparsingetcompose.R

@Composable
fun ConnectionScreen(
    navController: NavController
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val auth = Firebase.auth
    val mContext = LocalContext.current as Activity

    Scaffold {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo app",
                modifier = Modifier.padding(16.dp)
            )
            OutlinedTextField(
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = email.value,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedLabelColor = MaterialTheme.colors.primary
                ),
                onValueChange = { email.value = it })

            OutlinedTextField(
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                value = password.value,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedLabelColor = MaterialTheme.colors.primary
                ),
                onValueChange = { password.value = it })

            Row {
                Button(
                    onClick = {
                        navController.navigate(AppMainScreens.RegistrationScreen.route)
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(text = "Register", color = Color.White)
                }

                Button(
                    onClick = {
                        auth.signInWithEmailAndPassword(
                            email.value.trim(),
                            password.value.trim()
                        )
                            .addOnCompleteListener(mContext) { task ->
                                if (task.isSuccessful) {
                                    Log.d("Auth", "Success")
                                    navController.navigate(AppMainScreens.MainScreen.route)
                                } else {navController.navigate(AppMainScreens.MainScreen.route)
                                    Log.d("Auth", "Failed: ${task.exception}")
                                }
                            }
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(text = "Connect", color = Color.White)
                }
            }
        }
    }
}