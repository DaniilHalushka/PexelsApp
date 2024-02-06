package com.daniil.halushka.pexelsapp.presentation.screen.elements.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daniil.halushka.pexelsapp.domain.models.DomainFeaturedCollection

@Composable
fun FeatureCollectionsBar(
    idOfSelectedCollection: String,
    parts: List<DomainFeaturedCollection>,
    searchSelectedCollection: (String, String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        items(parts) {
            FeatureCollectionItem(
                nameOfCollection = it.title,
                isCollectionSelected = it.id == idOfSelectedCollection,
                onClick = {
                    searchSelectedCollection(
                        it.title,
                        it.id
                    )
                }
            )

        }
    }
}

@Composable
fun FeatureCollectionItem(
    nameOfCollection: String,
    isCollectionSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColorOfItem = if (isCollectionSelected)
        MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary

    val textColor = if (isCollectionSelected)
        Color.White else MaterialTheme.colorScheme.onBackground

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(128.dp))
            .background(backgroundColorOfItem)
            .clickable { onClick() },
    ) {
        Text(
            text = nameOfCollection,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp),
            color = textColor,
            fontSize = 14.sp
        )
    }
}