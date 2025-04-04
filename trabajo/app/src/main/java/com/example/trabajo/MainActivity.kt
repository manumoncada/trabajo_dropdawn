package com.example.trabajo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trabajo.ui.theme.TrabajoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrabajoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var texto by remember { mutableStateOf("") }
    var colorExpand by remember { mutableStateOf(false) }
    var fontExpand by remember { mutableStateOf(false) }

    var selectedColor by remember { mutableStateOf(Color.Black) }
    var selectedFont by remember { mutableStateOf(FontFamily.Default) }
    var selectedFontWeight by remember { mutableStateOf(FontWeight.Normal) }
    var selectedFontStyle by remember { mutableStateOf(FontStyle.Normal) }
    var selectedColorName by remember { mutableStateOf("Negro") }

    val coloresMap = mapOf(
        "Negro" to Color.Black,
        "Rojo" to Color.Red,
        "Azul" to Color.Blue,
        "Verde" to Color.Green,
        "Morado" to Color(0xFF800080)
    )

    val colores = coloresMap.keys.toList()
    val fuentes = listOf("Normal", "Negrita", "Cursiva", "Monospace", "Negrita+Cursiva")


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Ingresa tu texto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        ExposedDropdownMenuBox(
            expanded = colorExpand,
            onExpandedChange = { colorExpand = !colorExpand }
        ) {
            OutlinedTextField(
                value = selectedColorName,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = colorExpand) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = colorExpand,
                onDismissRequest = { colorExpand = false }
            ) {
                colores.forEach { color ->
                    DropdownMenuItem(
                        text = { Text(color) },
                        onClick = {
                            selectedColorName = color
                            selectedColor = coloresMap[color]!!
                            colorExpand = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ExposedDropdownMenuBox(
            expanded = fontExpand,
            onExpandedChange = { fontExpand = !fontExpand }
        ) {
            OutlinedTextField(
                value = when {
                    selectedFontWeight == FontWeight.Bold && selectedFontStyle == FontStyle.Italic -> "Negrita+Cursiva"
                    selectedFontWeight == FontWeight.Bold -> "Negrita"
                    selectedFontStyle == FontStyle.Italic -> "Cursiva"
                    selectedFont == FontFamily.Monospace -> "Monospace"
                    else -> "Normal"
                },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = fontExpand) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = fontExpand,
                onDismissRequest = { fontExpand = false }
            ) {
                fuentes.forEach { font ->
                    DropdownMenuItem(
                        text = { Text(font) },
                        onClick = {
                            selectedFontWeight = when (font) {
                                "Negrita", "Negrita+Cursiva" -> FontWeight.Bold
                                else -> FontWeight.Normal
                            }
                            selectedFontStyle = when (font) {
                                "Cursiva", "Negrita+Cursiva" -> FontStyle.Italic
                                else -> FontStyle.Normal
                            }
                            selectedFont = when (font) {
                                "Monospace" -> FontFamily.Monospace
                                else -> FontFamily.Default
                            }
                            fontExpand = false
                        }
                    )
                }
            }
        }

        Text(
            text = texto.ifEmpty { "..." },
            fontSize = 24.sp,
            fontWeight = selectedFontWeight,
            fontFamily = selectedFont,
            fontStyle = selectedFontStyle,
            color = selectedColor
        )
    }
}