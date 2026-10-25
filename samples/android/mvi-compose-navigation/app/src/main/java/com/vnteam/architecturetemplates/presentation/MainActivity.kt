package com.vnteam.architecturetemplates.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import cafe.adriel.voyager.navigator.Navigator
import com.vnteam.architecturetemplates.presentation.navigation.NavigationScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Navigator(NavigationScreen.ListContentScreen())
            }
        }
    }

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}