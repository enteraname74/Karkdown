import org.koin.dsl.module
import viewmodel.MainScreenViewModel

val appModule = module {
    single {
        MainScreenViewModel(
            fileManager = get(),
            lineAnalyzer = get()
        )
    }
}