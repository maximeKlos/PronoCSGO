package com.example.testparsingetcompose.ui.screens.authentication

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testparsingetcompose.data.tools.Check
import com.example.testparsingetcompose.ui.navigation.AppMainScreens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun RegistrationScreen(
    navController: NavController,
) {
    Scaffold {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val mContext = LocalContext.current as Activity

            val email = remember { mutableStateOf("") }
            val repeatEmail = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            val repeatPassword = remember { mutableStateOf("") }
            val nickname = remember { mutableStateOf("") }

            val outlinedTextModifier = Modifier.fillMaxWidth().padding(8.dp)
            val outlinedTextColor = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colors.primary,
                unfocusedLabelColor = MaterialTheme.colors.primary
            )

            OutlinedTextField(
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = email.value,
                singleLine = true,
                modifier = outlinedTextModifier,
                colors = outlinedTextColor,
                onValueChange = { email.value = it }
            )
            OutlinedTextField(
                label = { Text(text = "Repeat Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = repeatEmail.value,
                singleLine = true,
                modifier = outlinedTextModifier,
                colors = outlinedTextColor,
                onValueChange = { repeatEmail.value = it }
            )
            OutlinedTextField(
                label = { Text(text = "Nickname") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                value = nickname.value,
                singleLine = true,
                modifier = outlinedTextModifier,
                colors = outlinedTextColor,
                onValueChange = { nickname.value = it }
            )
            OutlinedTextField(
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                value = password.value,
                singleLine = true,
                modifier = outlinedTextModifier,
                colors = outlinedTextColor,
                onValueChange = { password.value = it },
            )
            OutlinedTextField(
                label = { Text(text = "Repeat Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                value = repeatPassword.value,
                singleLine = true,
                modifier = outlinedTextModifier,
                colors = outlinedTextColor,
                onValueChange = { repeatPassword.value = it }
            )

            Button(
                onClick = {
                    if(Check.isValidEmail(email.value)){
                        if(email.value == repeatEmail.value){
                            if(Check.passwordContainsAllCharacteristics(password.value)){
                                if(password.value == repeatPassword.value){
                                    Firebase.auth.createUserWithEmailAndPassword(
                                        email.value.trim(),
                                        password.value.trim()
                                    )
                                        .addOnCompleteListener(mContext) { task ->
                                            if (task.isSuccessful) {
                                                Log.d("AuthLog", "Success")
                                                val db = Firebase.firestore
                                                val user = hashMapOf(
                                                    "isAdmin" to false,
                                                    "nickname" to nickname.value
                                                )
                                                db.collection("users")
                                                    .document(Firebase.auth.currentUser!!.uid)
                                                    .set(user)
                                                    .addOnFailureListener { e ->
                                                        Log.d("Users",e.message.toString())
                                                    }
                                                navController.navigate(AppMainScreens.MainScreen.route)
                                            } else {
                                                Log.d("AuthLog", "Failed: ${task.exception}")
                                                email.value = ""
                                                password.value = ""
                                                repeatPassword.value = ""
                                                repeatEmail.value = ""
                                                nickname.value = ""
                                                Toast.makeText(mContext,"Registration failed", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                } else Toast.makeText(mContext, "Passwords are not equals", Toast.LENGTH_LONG).show()
                            } else Toast.makeText(mContext, "Password must be >8 characters and contains Lowercase, Uppercase, Number and Special characters", Toast.LENGTH_LONG).show()
                        } else Toast.makeText(mContext, "Emails are not equals", Toast.LENGTH_LONG).show()
                    } else Toast.makeText(mContext,"Email invalid", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(text = "Register", color = Color.White)
            }
        }
    }
}