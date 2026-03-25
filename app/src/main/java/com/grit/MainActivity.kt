package com.grit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.grit.ui.GritNavigation
import com.grit.ui.theme.GritTheme
import com.grit.ui.theme.GritWhite

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GritTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = GritWhite
                ) {
                    GritNavigation()
                }
            }
        }
    }
}
