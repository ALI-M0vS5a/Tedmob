package dev.alimoussa.tedmob.ui.designsystem

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import dev.alimoussa.tedmob.R

@Composable
fun ConfirmAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onDismissRequest: () -> Unit = onDismiss,
    confirmButtonText: String = stringResource(id = R.string.confirm),
    cancelButtonText: String = stringResource(id = R.string.cancel),
    confirmButton: @Composable () -> Unit = {
        Button(
            onClick = {
                onConfirm()
                onDismiss()
            }
        ) {
            Text(text = confirmButtonText)
        }
    },
    dismissButton: @Composable () -> Unit = {
        Button(
            onClick = onDismiss
        ) {
            Text(text = cancelButtonText)
        }
    },
    dialogProperties: DialogProperties = ConfirmDialogDefaults.dialogProperties
) {
    val configuration = LocalConfiguration.current
    AlertDialog(
        modifier = modifier
            .widthIn(max = configuration.screenWidthDp.dp - ConfirmDialogDefaults.dialogMargin * 2),
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        title = { Text(text = title) },
        text = { Text(text = message) },
        properties = dialogProperties
    )
}

object ConfirmDialogDefaults {
    val dialogProperties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
        dismissOnBackPress = true,
        dismissOnClickOutside = true,
        decorFitsSystemWindows = true
    )
    val dialogMargin: Dp = 16.dp
}