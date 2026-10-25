package com.vnteam.architecturetemplates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.compose.koinInject
import presentation.App
import presentation.setActivityProvider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityProvider { this }
        setContent {
            App(koinInject())
        }
    }
}