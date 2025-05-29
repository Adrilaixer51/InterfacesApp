package com.example.primetea

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioSesion(navController: NavController) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9)) // fondo verdoso suave
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Logo superior
            Image(
                painter = painterResource(id = R.drawable.logo), // Asegúrate de tener este recurso
                contentDescription = "Logo PrimeTea",
                modifier = Modifier
                    .height(160.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "Iniciar sesión",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = {
                    Text(
                        "Correo electrónico",
                        color = Color(0xFF2E7D32)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = {
                    Text(
                        "Contraseña",
                        color = Color(0xFF2E7D32)
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Aquí puedes validar e iniciar sesión
                     navController.navigate("home") // Cambia esto por la pantalla a la que quieras navegar
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión", fontSize = 20.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto clicable para navegar a Registro
            Text(
                text = "¿No tienes cuenta? Regístrate",
                color = Color(0xFF2E7D32),
                fontSize = 14.sp,
                style = TextStyle(fontWeight = FontWeight.Medium),
                modifier = Modifier.clickable {
                    navController.navigate("registro") // asegúrate de usar el mismo nombre que en el NavHost
                }
            ) 
        }
    }
}
