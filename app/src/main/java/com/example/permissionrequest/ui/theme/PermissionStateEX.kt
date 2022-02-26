package com.example.permissionrequest.ui.theme

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isDisallowed() = !shouldShowRationale && !hasPermission