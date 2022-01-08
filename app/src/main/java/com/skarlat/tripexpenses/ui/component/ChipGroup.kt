package com.skarlat.tripexpenses.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skarlat.tripexpenses.ui.model.ChipItem
import com.skarlat.tripexpenses.utils.Const

@Composable
fun ChipGroup(
    items: List<ChipItem>,
    selectedItems: Iterable<String>,
    onItemSelected: (String) -> Unit
) {
    LazyRow() {
        item {
            Spacer(modifier = Modifier.width(8.dp))
        }
        items(items) { item ->
            Row() {
                RadioButton(
                    selected = item.id in selectedItems,
                    onClick = { onItemSelected.invoke(item.id) })
                Text(text = item.name, modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}


@Composable
@Preview
fun PreviewChipGroup() {
    val allChipItem = ChipItem(id = Const.ALL_ID, name = "Все")
    val items = listOf<ChipItem>(allChipItem, ChipItem(name = "Вася"), ChipItem(name = "Эльвина"))
    val selectedItems = remember {
        mutableStateListOf<String>()
    }
    Box(Modifier.fillMaxWidth()) {
        ChipGroup(items = items, selectedItems = selectedItems, onItemSelected = { itemId: String ->
            if (!selectedItems.removeAll { itemId == allChipItem.id || it == itemId }) {
                if (itemId == allChipItem.id) {
                    selectedItems.addAll(items.map { it.id })
                } else {
                    selectedItems.add(itemId)
                    if (selectedItems.size == items.size - 1)
                        selectedItems.add(allChipItem.id)
                }
            }
        })
    }
}