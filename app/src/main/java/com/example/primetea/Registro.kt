package com.example.primetea

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(navController: NavController) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    var nombreHijo by remember { mutableStateOf("") }
    var apellidosHijo by remember { mutableStateOf("") }
    var edadHijo by remember { mutableStateOf("") }
    var telefonoHijo by remember { mutableStateOf("") }

    var fechaDia by remember { mutableStateOf("") }
    var fechaMes by remember { mutableStateOf("") }
    var fechaAnio by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Registro",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campos de correo y contraseña
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico", color = Color(0xFF2E7D32)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña", color = Color(0xFF2E7D32)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Rectángulo azul para información del hijo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD0E8F2), shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Información del hijo",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = nombreHijo,
                    onValueChange = { nombreHijo = it },
                    label = { Text("Nombre del hijo", color = Color(0xFF2E7D32)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = apellidosHijo,
                    onValueChange = { apellidosHijo = it },
                    label = { Text("Apellidos del hijo", color = Color(0xFF2E7D32)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = edadHijo,
                        onValueChange = { edadHijo = it },
                        label = { Text("Edad", color = Color(0xFF2E7D32)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(color = Color.Black),
                        modifier = Modifier.weight(1f)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(2f)
                    ) {
                        OutlinedTextField(
                            value = fechaDia,
                            onValueChange = { fechaDia = it },
                            label = { Text("", color = Color(0xFF2E7D32)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(color = Color.Black),
                            modifier = Modifier.width(48.dp)
                        )
                        Text(text = "/", modifier = Modifier.padding(horizontal = 4.dp))
                        OutlinedTextField(
                            value = fechaMes,
                            onValueChange = { fechaMes = it },
                            label = { Text("", color = Color(0xFF2E7D32)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(color = Color.Black),
                            modifier = Modifier.width(48.dp)
                        )
                        Text(text = "/", modifier = Modifier.padding(horizontal = 4.dp))
                        OutlinedTextField(
                            value = fechaAnio,
                            onValueChange = { fechaAnio = it },
                            label = { Text("", color = Color(0xFF2E7D32)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(color = Color.Black),
                            modifier = Modifier.width(73.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = telefonoHijo,
                    onValueChange = { telefonoHijo = it },
                    label = { Text("Teléfono móvil del hijo", color = Color(0xFF2E7D32)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Aquí puedes guardar o navegar
                     navController.navigate("home") // Navegar a la pantalla de inicio
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse", fontSize = 20.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¿Ya tienes cuenta? Inicia sesión",
                color = Color(0xFF2E7D32),
                fontSize = 14.sp,
                style = TextStyle(fontWeight = FontWeight.Medium),
                modifier = Modifier.clickable {
                    navController.navigate("inicioSesion")
                }
            )
        }
    }
}
