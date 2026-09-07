package com.emergency.routing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.emergency.routing.navigation.AppNavGraph
import com.emergency.routing.ui.components.MainAppScaffold
import com.emergency.routing.ui.theme.EmergencyFleetRoutingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmergencyFleetRoutingTheme {
                Surface {
                    val navController = rememberNavController()
                    MainAppScaffold(navController = navController) { innerPaddingModifier ->
                        AppNavGraph(
                            navController = navController,
                            modifier = innerPaddingModifier
                        )
                    }
                }
            }
        }
    }
}
