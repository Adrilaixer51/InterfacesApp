package com.example.primetea

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Configuracion2Screen(navController: NavController) {
    var sonidoActivado by remember { mutableStateOf(true) }
    var nuevaContra by remember { mutableStateOf("") }
    var repetirContra by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(16.dp)
    ) {
        // Top bar con back y logo centrado
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate("home")}) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.Black)
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo PrimeTea",
                modifier = Modifier
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            // placeholder para mantener el logo centrado
            Box(modifier = Modifier.size(48.dp)) { }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sonido activado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Sonido Activado",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2E7D32)
            )
            Switch(
                checked = sonidoActivado,
                onCheckedChange = { sonidoActivado = it },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color(0xFF66BB6A),
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Cambiar contraseña
        Text(
            "Cambiar Contraseña",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2E7D32),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = nuevaContra,
            onValueChange = { nuevaContra = it },
            label = { Text("Nueva contraseña", color = Color(0xFF2E7D32)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = repetirContra,
            onValueChange = { repetirContra = it },
            label = { Text("Repetir contraseña", color = Color(0xFF2E7D32)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        // Botón Aceptar Cambios
        Button(
            onClick = {
                // lógica de guardado...
                navController.navigate("home")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text("Aceptar Cambios", fontSize = 18.sp, color = Color.White)
        }
    }
}
