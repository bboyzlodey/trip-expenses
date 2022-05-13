package com.skarlat.tripexpenses.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectedChip(isSelected: Boolean, text: String, onSelectedChanged: (Boolean) -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        onClick = { onSelectedChanged.invoke(!isSelected) },
        indication = rememberRipple()
    ) {
        Row(Modifier.padding(all = 6.dp)) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.selected_chip_icon_description)
                    )
                }
            }
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
    }
}

@Composable
@Preview
fun PreviewSelectedChip() {
    var isSelected by remember {
        mutableStateOf(false)
    }
    SelectedChip(
        isSelected = isSelected,
        text = "Денис Петров",
        onSelectedChanged = { isSelected = it })
}