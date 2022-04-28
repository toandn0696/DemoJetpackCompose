package com.example.composepokemondexproject.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.composepokemondexproject.ui.main.MainActivity
import com.example.composepokemondexproject.common.theme.ComposePokemonDexProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

/**
 * Create by Nguyen Thanh Toan on 10/4/21
 *
 */

@ExperimentalFoundationApi
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePokemonDexProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    UiSplash()
                }
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000L)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
    }


    @Composable
    private fun UiSplash() {
        Text(text = "Splash")
    }
}
