package org.lenatin.pulse.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.lenatin.pulse.model.ShareTarget

@Composable
internal fun ShareRow(onShare: (ShareTarget) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
        ShareChip("Messages", Icons.Default.Sms) { onShare(ShareTarget.Messages) }
        ShareChip("Photos", Icons.Default.PhotoLibrary) { onShare(ShareTarget.Photos) }
        ShareChip("Link", Icons.Default.Link) { onShare(ShareTarget.Link) }
    }
}

@Composable
private fun ShareChip(label: String, icon: ImageVector, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, shape = RoundedCornerShape(12.dp)) {
        Icon(icon, null)
        // Spacer(Modifier.width(8.dp))
        Text(label)
    }
}
