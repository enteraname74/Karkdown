package composable

import Constants
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import theme.KarkdownColorTheme
import utils.FileHeader

@Composable
fun FileHeaders(
    headers: List<FileHeader>,
    onHeaderClicked: (headerPos: Int) -> Unit,
    onCreateFile: () -> Unit,
    onCloseFile: (headerPos: Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    Row {
        Box (
            modifier = Modifier
                .height(40.dp)
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .horizontalScroll(scrollState)
            ) {
                Row {
                    headers.forEachIndexed { index, fileHeader ->
                        FileHeaderView(
                            header = fileHeader,
                            onClick = {
                                onHeaderClicked(index)
                            },
                            onClose = {
                                onCloseFile(index)
                            }
                        )
                    }
                }
            }

            HorizontalScrollbar(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(),
                adapter = rememberScrollbarAdapter(scrollState),
                style = defaultScrollbarStyle().copy(
                    minimalHeight = 8.dp,
                    thickness = 6.dp,
                    shape = RoundedCornerShape(corner = CornerSize(2.dp)),
                    unhoverColor = KarkdownColorTheme.colorScheme.primaryContainer,
                    hoverColor = KarkdownColorTheme.colorScheme.onPrimary
                )
            )
        }
        Icon(
            modifier = Modifier
                .background(color = KarkdownColorTheme.colorScheme.primary)
                .padding(end = Constants.Spacing.medium)
                .size(Constants.ImageSize.medium)
                .clickable {
                    onCreateFile()
                },
            imageVector = Icons.Rounded.Add,
            contentDescription = "",
            tint = KarkdownColorTheme.colorScheme.onPrimary
        )
    }
}