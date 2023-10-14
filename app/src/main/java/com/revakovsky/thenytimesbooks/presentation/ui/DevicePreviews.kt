package com.revakovsky.thenytimesbooks.presentation.ui

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "1_Phone_Small",
    group = "Devices",
    device = "spec:width=480px,height=800px,dpi=320",
    showSystemUi = true,
    showBackground = true,
)

@Preview(
    name = "3_Phone_Medium",
    group = "Devices",
    device = "spec:width=480px,height=800px,dpi=160",
    showSystemUi = true,
    showBackground = true,
)

@Preview(
    name = "4_Tablet_Expanded",
    group = "Devices",
    device = "spec:parent=Nexus 7",
    showSystemUi = true,
    showBackground = true,
)

@Preview(
    name = "5_Desktop_Expanded",
    group = "Devices",
    device = "spec:width=1920dp,height=1080dp,dpi=160",
    showSystemUi = true,
    showBackground = true,
)

annotation class DevicePreviews