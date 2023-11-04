package uk.ac.tees.w9623647.parkme.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
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
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center) {
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

fun updateDisplayName(name: String?) {
    val user = Firebase.auth.currentUser
    val profileUpdate = userProfileChangeRequest {
        displayName = name
    }
    user!!.updateProfile(profileUpdate)
}
