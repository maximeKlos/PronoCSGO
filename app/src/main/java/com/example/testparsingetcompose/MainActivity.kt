package com.example.testparsingetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.testparsingetcompose.ui.navigation.NavigationApp
import com.example.testparsingetcompose.ui.theme.TestParsingEtComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestParsingEtComposeTheme {
                NavigationApp(context = this)
            }
        }
    }
}
