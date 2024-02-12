package com.daniil.halushka.pexelsapp.presentation.screen.elements.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.daniil.halushka.pexelsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    userRequest: String,
    userRequestsHistory: MutableList<String>,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
) {
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .padding(horizontal = if (active) 0.dp else 24.dp),
        query = userRequest,
        onQueryChange = onQueryChanged,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        placeholder = {
            Text(text = stringResource(id = R.string.custom_search_bar_placeholder))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(id = R.string.custom_search_bar_icon_description),
                tint = MaterialTheme.colorScheme.tertiary
            )
        },
        trailingIcon = {
            if (active) {
                ClearIcon(onQueryChanged, userRequest, onActiveChange)
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primary,
            inputFieldColors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    ) {
        HistoryList(userRequestsHistory, onQueryChanged)
    }
}

@Composable
fun ClearIcon(
    onQueryChanged: (String) -> Unit,
    userRequest: String,
    onActiveChange: (Boolean) -> Unit
) {
    Icon(
        imageVector = Icons.Default.Clear,
        contentDescription = stringResource(id = R.string.custom_search_bar_icon_description),
        modifier = Modifier.clickable {
            if (userRequest.isNotBlank()) {
                onQueryChanged("")
            } else {
                onActiveChange(false)
            }
        },
    )
}

@Composable
fun HistoryList(
    userRequestsHistory: List<String>,
    onQueryChanged: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(items = userRequestsHistory) { historyItem ->
            HistoryItem(historyItem, onQueryChanged)
        }
    }
}

@Composable
fun HistoryItem(historyItem: String, onQueryChanged: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .clickable { onQueryChanged(historyItem) }
    ) {
        Icon(
            modifier = Modifier.padding(end = 12.dp),
            painter = painterResource(id = R.drawable.ic_history),
            contentDescription = stringResource(id = R.string.history_icon_description)
        )
        Text(text = historyItem)
    }
}
