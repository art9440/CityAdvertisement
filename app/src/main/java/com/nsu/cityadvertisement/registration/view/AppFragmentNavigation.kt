package com.nsu.cityadvertisement.registration.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nsu.cityadvertisement.account.AccountScreen
import com.nsu.cityadvertisement.mapscreen.MapScreen
import com.nsu.cityadvertisement.view.LoginScreen


@Composable
fun AppNavigator(paddingValues: PaddingValues) {
    val navController = rememberNavController();
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, paddingValues) }
        composable("register") { RegistrationScreen(navController, paddingValues) }
        composable("forgot") { ForgetPasswordScreen(navController, paddingValues) }
        composable("map") { MapScreen(navController = navController, paddingValues = paddingValues) }
        composable("account") { AccountScreen(navController = navController, paddingValues)}
    }

}
