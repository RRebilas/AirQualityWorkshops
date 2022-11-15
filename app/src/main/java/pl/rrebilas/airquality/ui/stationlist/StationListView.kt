package pl.rrebilas.airquality.ui.stationlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun StationListScreen() {
    val viewModel: StationsListViewModel = hiltViewModel()
    val state = viewModel.state
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = state.isRefreshing),
        onRefresh = { viewModel.onPullToRefresh() }) {
        LazyColumn {
            items(viewModel.state.stations) {
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(it.imageUrl)
                                .build(),
                            contentDescription = "Station image",
                            modifier = Modifier.size(80.dp)
                        )
                        Column(
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Text(text = it.title, style = MaterialTheme.typography.titleMedium)
                            Text(text = it.subtitle, style = MaterialTheme.typography.titleMedium)
                            Text(text = it.label, style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }
    }
}
