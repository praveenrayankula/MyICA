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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import uk.ac.tees.w9623647.parkme.ui.LocationPermissionScreen
import uk.ac.tees.w9623647.parkme.ui.MapScreen
import uk.ac.tees.w9623647.parkme.utils.checkForPermission

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val user = Firebase.auth.currentUser
    val context = LocalContext.current
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

               var hasLocationPermission by  remember {
                    mutableStateOf(checkForPermission(context))
                }

                if (hasLocationPermission) {
                    MapScreen(context)
                } else {
                    LocationPermissionScreen {
                        hasLocationPermission = true
                    }
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
