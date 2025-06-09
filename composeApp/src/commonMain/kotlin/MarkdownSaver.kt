import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import com.google.gson.Gson

internal object MarkdownSaver : Saver<MarkdownManager, String> {
    override fun restore(value: String): MarkdownManager? {
        val gson = Gson()
        val savableMarkdownManager: SavableMarkdownManager = gson.fromJson(
            value,
            SavableMarkdownManager::class.java
        )
        return MarkdownManager().apply {
            init(lines = savableMarkdownManager.lines)
        }
    }

    override fun SaverScope.save(value: MarkdownManager): String? {
        val gson = Gson()
        val savableMarkdownManager = SavableMarkdownManager.fromMarkdownManager(
            markdownManager = value,
        )

        return gson.toJson(savableMarkdownManager)
    }
}

internal data class SavableMarkdownManager(
    val lines: List<String>
) {
    companion object {
        fun fromMarkdownManager(markdownManager: MarkdownManager): SavableMarkdownManager =
            SavableMarkdownManager(
                lines = markdownManager.getRowData(),
            )
    }
}