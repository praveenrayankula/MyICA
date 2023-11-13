package uk.ac.tees.w9623647.parkme.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uk.ac.tees.w9623647.parkme.R

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Column (modifier = Modifier
            .weight(2f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            LoginInterface(navController)
            ForgetPassword(navController)
        }
        Column (modifier = Modifier
            .weight(.1f)){
            SignUp(navController)
        }
    }
}

@Composable
fun LoginInterface(
    navController: NavHostController
) {
    val auth = Firebase.auth

    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current  // context must be inside composable function
    val focusManager = LocalFocusManager.current    // focus manager

    Row (
        modifier = Modifier
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth(0.9f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Image(painter = painterResource(id = R.drawable.parkmelogoc),
                contentDescription = stringResource(id = R.string.friends),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp))

            //USERNAME TEXT FIELD
            OutlinedTextField(
                value = username,
                onValueChange = {
                    if (it.length <= 45)
                        username = it
                    if (it.isEmpty())
                        focusManager.clearFocus()
                },
                label = { Text(text = "Email") },
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                singleLine = true,
            )

            //PASSWORD TEXT FIELD
            OutlinedTextField(
                value = password,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    if (it.length <= 45)
                        password = it
                    if (it.isEmpty())
                        focusManager.clearFocus()
                },
                label = { Text(text = "Password") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            //LOGIN BUTTON
            Button(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp, 50.dp, 50.dp, 50.dp)),
                onClick = {
//                    if (username == "Bokz" && password == "Bokz") {
//                        Toast.makeText(context,"Login successful!", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(context,"Login failed!", Toast.LENGTH_SHORT).show()
//                    }
                    auth.signInWithEmailAndPassword(
                        username.trim(),
                        password.trim()
                    ).addOnCompleteListener() {task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context,"Login successful!", Toast.LENGTH_SHORT).show()
                            navController.navigate(route = Screen.HomeScreen.route)
                        } else {
                            Toast.makeText(context,"Login failed!", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context,task.exception.toString(), Toast.LENGTH_LONG).show()
                            println(task.exception.toString())
                        }
                    }

                }) {
                Text(
                    text = "Login",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun ForgetPassword(
    navController: NavHostController
) {
    Row (
    ){
        TextButton(
            onClick = {
                navController.navigate(route = Screen.ForgotPassword.route)
            }) {
            Text(text = "Forgot password?")
        }
    }
}

@Composable
fun SignUp(
    navController: NavHostController
) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "Don't have an account?")
        TextButton(onClick = {
            navController.navigate(route = Screen.Register.route)
        }) {
            Text(text = "Sign Up")
        }
    }
}