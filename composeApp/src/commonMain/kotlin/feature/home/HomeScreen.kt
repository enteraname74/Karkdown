package feature.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val elements by viewModel.elements.collectAsState()

    LazyColumn {
        items(items = elements) { element ->
            element.View(modifier = Modifier.fillMaxWidth())
        }
    }
}