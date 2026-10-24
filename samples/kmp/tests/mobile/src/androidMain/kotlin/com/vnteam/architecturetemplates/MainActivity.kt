package com.vnteam.architecturetemplates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.compose.koinInject
import com.vnteam.architecturetemplates.presentation.App
import com.vnteam.architecturetemplates.utils.setActivityProvider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityProvider { this }
        setContent {
            App(koinInject())
        }
    }
}