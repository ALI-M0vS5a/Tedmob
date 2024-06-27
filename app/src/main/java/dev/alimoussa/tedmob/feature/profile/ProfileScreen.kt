package dev.alimoussa.tedmob.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.alimoussa.tedmob.common.network.context.dialPhone
import dev.alimoussa.tedmob.common.network.context.sendEmail
import dev.alimoussa.tedmob.ui.designsystem.ConfirmAlertDialog

@Composable
fun ProfileRoute(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
) {
    val profileUiState by viewModel.profileUiState.collectAsStateWithLifecycle()
    ProfileScreen(
        modifier = modifier,
        profileScreenState = profileUiState,
        onLogout = viewModel::logout
    )
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileScreenState: ProfileScreenState,
    onLogout: () -> Unit = {}
) {
    var showConfirmAlertDialog by rememberSaveable { mutableStateOf(false) }

    when (profileScreenState) {
        ProfileScreenState.Loading -> Unit
        is ProfileScreenState.Success -> UserProfile(
            profileScreenState = profileScreenState,
            modifier = modifier,
            onLogout = { showConfirmAlertDialog = true }
        )
    }

    if (showConfirmAlertDialog) {
        ConfirmAlertDialog(
            title = "Logout",
            message = "Are you sure you want to logout?",
            onConfirm = {
                onLogout()
                showConfirmAlertDialog = false
            },
            onDismiss = {
                showConfirmAlertDialog = false
            }
        )
    }
}


@Composable
private fun UserProfile(
    profileScreenState: ProfileScreenState.Success,
    onLogout: () -> Unit = {},
    modifier: Modifier
) {
    val context = LocalContext.current
    val email = profileScreenState.email

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Rounded.Person,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "User Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        ProfileDetail("First Name", "John")
        ProfileDetail("Last Name", "Doe")
        ProfileDetail(
            "Email",
            email,
            onClick = { context.sendEmail(email) }
        )
        ProfileDetail(
            "Phone",
            "+1234567890",
            onClick = { context.dialPhone("+1234567890") }
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text("Logout", fontSize = 18.sp)
        }
    }
}

@Composable
private fun ProfileDetail(label: String, value: String, onClick: () -> Unit = {}) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        Text(text = label, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Text(text = value, fontSize = 16.sp)
    }
}
