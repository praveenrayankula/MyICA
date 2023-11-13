package uk.ac.tees.w9623647.parkme.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ForgotPasswordScreen(
    navController: NavHostController
) {
    val auth = Firebase.auth
    var username by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Scaffold(
        // TOP BAR AND ICONS
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "")
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
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 25.dp)
            ) {
                Text(
                    text = "Forgot password",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        if (it.length <= 45)
                            username = it
                    },
                    label = { Text(text = "Username") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 1,
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                              auth.sendPasswordResetEmail(username).addOnCompleteListener() {task ->
                                  if (task.isSuccessful) {
                                      Toast.makeText(context,"Email Sent successful!", Toast.LENGTH_SHORT).show()
                                      navController.navigate(route = Screen.Login.route)
                                  } else {
                                      Toast.makeText(context,"Email sent failed!", Toast.LENGTH_SHORT).show()
                                      Toast.makeText(context,task.exception.toString(), Toast.LENGTH_LONG).show()
                                      println(task.exception.toString())
                                  }
                              }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(50.dp, 50.dp, 50.dp, 50.dp))
                ) {
                    Text(
                        text = "Send verification",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }

}

@Composable
@Preview
fun ForgotPasswordPreview(

) {
    ForgotPasswordScreen(rememberNavController())
}
