package com.daniil.halushka.pexelsapp.presentation.screen.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daniil.halushka.pexelsapp.R

@Composable
fun CustomTopBar(
    author: String,
    isNavigationExist: Boolean,
    clickOnNavigationItem: () -> Unit = {}
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val contentColor = MaterialTheme.colorScheme.onBackground

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isNavigationExist) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    IconButton(
                        onClick = clickOnNavigationItem,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp)),
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.custom_top_bar_back_button
                            ),
                            tint = contentColor
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.weight(1f),
                text = author,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}