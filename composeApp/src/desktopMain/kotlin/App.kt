import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import di.mainModule
import feature.home.HomeScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinApplication(
            application = {
                modules(mainModule)
            }
        ) {
            HomeScreen(
                viewModel = koinViewModel()
            )
        }
    }
}