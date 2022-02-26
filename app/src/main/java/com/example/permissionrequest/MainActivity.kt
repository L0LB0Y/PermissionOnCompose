package com.example.permissionrequest

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.permissionrequest.ui.theme.PermissionRequestTheme
import com.example.permissionrequest.ui.theme.isDisallowed
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PermissionRequestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Start()
                }
            }
        }
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Start() {
    val permissionStat = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    )
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                permissionStat.launchMultiplePermissionRequest()
            }

        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        permissionStat.permissions.forEach { permission ->
            when (permission.permission) {
                Manifest.permission.CAMERA -> {
                    when {
                        permission.hasPermission -> {
                            Text(text = "Manifest.permission.CAMERA Accept")
                        }
                        permission.shouldShowRationale -> {
                            Text(text = "Manifest.permission.CAMERA Need the access")
                        }
                        permission.isDisallowed() -> {
                            Text(
                                text = "Manifest.permission.CAMERA Was Disallowed You " +
                                        "Should Go And Allowed It From Settings"
                            )
                        }
                    }
                }
                Manifest.permission.RECORD_AUDIO -> {
                    when {
                        permission.hasPermission -> {
                            Text(text = "Manifest.permission.RECORD_AUDIO Accept")
                        }
                        permission.shouldShowRationale -> {
                            Text(text = "Manifest.permission.RECORD_AUDIO Need the access")
                        }
                        permission.isDisallowed() -> {
                            Text(
                                text = "Manifest.permission.RECORD_AUDIO Was Disallowed You " +
                                        "Should Go And Allowed It From Settings"
                            )
                        }
                    }
                }
            }
        }
    }
}
