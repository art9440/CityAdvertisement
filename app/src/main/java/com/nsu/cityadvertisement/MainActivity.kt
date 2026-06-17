package com.nsu.cityadvertisement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.nsu.cityadvertisement.ui.theme.CityAdvertisementTheme
import com.nsu.cityadvertisement.registration.view.AppNavigator
import com.yandex.mapkit.MapKitFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey("6a36e86c-ada6-4af3-b82a-b7b9efabbb8c")
        MapKitFactory.initialize(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CityAdvertisementTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                 AppNavigator(paddingValues = innerPadding)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}

