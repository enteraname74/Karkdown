import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposeWindow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import screen.MainScreen
import viewmodel.MainScreenViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App(
    window: ComposeWindow
) {
    MaterialTheme {
        KoinApplication(
            application = {
                modules(appModule)
            }
        ) {
            val mainScreenViewModel = koinInject<MainScreenViewModel>()

            MainScreen(
                mainScreenViewModel = mainScreenViewModel,
                window = window
            )
        }
    }
}