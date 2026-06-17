package com.nsu.cityadvertisement.mapscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.Animation
import com.yandex.runtime.image.ImageProvider
import com.nsu.cityadvertisement.R



@Composable
fun MapScreen(navController: NavController, paddingValues: PaddingValues) {
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var isDrawerOpen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    isDrawerOpen = true
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Меню")
                }
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->

            AndroidView(
                factory = { context ->
                    MapView(context).apply {
                        val center = Point(55.030199, 82.920430)

                        val south = 54.95
                        val north = 55.15
                        val west = 82.80
                        val east = 83.05

                        map.move(
                            CameraPosition(center, 12.0f, 0.0f, 0.0f),
                            Animation(Animation.Type.SMOOTH, 0.5f),
                            null
                        )

                        val placemark = map.mapObjects.addPlacemark(center)
                        placemark.setIcon(ImageProvider.fromResource(context, R.drawable.ic_launcher_foreground))
                        placemark.addTapListener { _, _ ->
                            Log.d("Map", "Marker clicked!")
                            true
                        }

                        map.addCameraListener { _, cameraPosition, _, final ->
                            if (!final) return@addCameraListener

                            val lat = cameraPosition.target.latitude
                            val lon = cameraPosition.target.longitude

                            val clampedLat = lat.coerceIn(south, north)
                            val clampedLon = lon.coerceIn(west, east)

                            if (lat != clampedLat || lon != clampedLon) {
                                map.move(
                                    CameraPosition(
                                        Point(clampedLat, clampedLon),
                                        cameraPosition.zoom,
                                        cameraPosition.azimuth,
                                        cameraPosition.tilt
                                    ),
                                    Animation(Animation.Type.SMOOTH, 0.3f),
                                    null
                                )
                            }
                        }

                        mapView = this
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }


        CustomDrawer(
            isOpen = isDrawerOpen,
            onClose = { isDrawerOpen = false }
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            DrawerSectionItem(
                icon = Icons.Rounded.AccountCircle,
                text = "Account",
                onClick = {
                    navController.navigate("account")
                    isDrawerOpen = false
                }
            )

        }
    }
}

@Composable
fun DrawerSectionItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
    }
}

@Composable
fun CustomDrawer(
    isOpen: Boolean,
    onClose: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    if (isOpen) {
        Box(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(280.dp)
                    .background(Color.White)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { change, dragAmount ->
                            if (dragAmount < -50) {
                                onClose()
                            }
                        }
                    }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    content()
                }
            }
        }
    }
}



