package feature.home

import androidx.lifecycle.ViewModel
import com.github.enteraname74.karkdowncore.FileManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class HomeViewModel : ViewModel(), HomeAction {
    private val fileManager: FileManager = FileManager()
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(
        HomeState(
            markdownElements = fileManager.content
        )
    )
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private fun updateState() {
        _state.value = HomeState(
            markdownElements = fileManager.content,
        )
    }

    override fun setLine(pos: Int, line: String) {
        fileManager.updateLineAt(
            line = line,
            pos = pos,
        )
        updateState()
    }


}