package com.captaindeer.erik_rucksack.ui.screens

/**
 * Created by suffered on 18/03/25
 */

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.captaindeer.erik_rucksack.R
import com.captaindeer.erik_rucksack.ui.viewmodels.LoginViewModel

@Composable
fun FirebaseScreen() {
    Text("Ajua perro")
}

@Composable
fun InitialScreen(navigateToLogin: () -> Unit = {}, navigateToSignUp: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Gray, Black), startY = 0f, endY = 600f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "",
            modifier = Modifier.clip(CircleShape)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Millions of songs.",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Free on Spotify", color = Color.White, fontSize = 38.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navigateToSignUp() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green)
        ) {
            Text(text = "Sign up free", color = Black, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            Modifier.clickable { },
            painterResource(id = R.drawable.ic_launcher_foreground),
            "Continue with Google"
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            Modifier.clickable { },
            painterResource(id = R.drawable.ic_launcher_foreground),
            "Continue with Facebook"
        )
        Text(
            text = "Log In",
            color = Color.White,
            modifier = Modifier
                .padding(24.dp)
                .clickable { navigateToLogin() },
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CustomButton(modifier: Modifier, painter: Painter, title: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(16.dp)
        )
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LoginScreen(viewModel: LoginViewModel, navigateToHome: () -> Unit) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Email", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = email,
            onValueChange = viewModel::onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Gray,
                focusedContainerColor = Color.White
            )
        )
        Spacer(Modifier.height(48.dp))
        Text("Password", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Gray,
                focusedContainerColor = Color.White
            )
        )
        Spacer(Modifier.height(48.dp))
        Button(onClick = { viewModel.login(navigateToHome) }) {
            Text(text = "Login")
        }
    }
}

@Composable
fun SignUpScreen(viewModel: LoginViewModel, navigateToHome: () -> Unit) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Email", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = email,
            onValueChange = viewModel::onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Gray,
                focusedContainerColor = Color.White
            )
        )
        Spacer(Modifier.height(48.dp))
        Text("Password", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Gray,
                focusedContainerColor = Color.White
            )
        )
        Spacer(Modifier.height(48.dp))
        Button(onClick = { viewModel.signUp(navigateToHome) }) {
            Text(text = "Sign Up")
        }
    }
}
