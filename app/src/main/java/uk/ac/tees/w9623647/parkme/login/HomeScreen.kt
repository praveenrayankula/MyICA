package uk.ac.tees.w9623647.parkme.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val user = Firebase.auth.currentUser

    Scaffold(
        // TOP BAR AND ICONS
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Logout")
                },
                navigationIcon = {
                    // POP BACKSTACK TO LOGIN SCREEN
                    IconButton(onClick = {
                        navController.navigate(Screen.Login.route){
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back")
                    }
                }
            )
        }
    ) { values ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Column {
                user?.let {
                    val name = it.email
                    val displayName = it.email
                    updateDisplayName(displayName)
                    Text(text = "Hi, $name")
                }
            }
        }
    }
}

fun updateDisplayName(name: String?) {
    val user = Firebase.auth.currentUser
    val profileUpdate = userProfileChangeRequest {
        displayName = name
    }
    user!!.updateProfile(profileUpdate)
}
