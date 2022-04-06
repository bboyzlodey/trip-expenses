package com.skarlat.tripexpenses.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.BuildConfig
import com.skarlat.tripexpenses.R

@Composable
@Preview(showBackground = true)
fun ColumnScope.AppVersion() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.CenterHorizontally)) {
        Text(
            text = stringResource(id = R.string.app_version, BuildConfig.VERSION_NAME),
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.Center)
        )
    }
}