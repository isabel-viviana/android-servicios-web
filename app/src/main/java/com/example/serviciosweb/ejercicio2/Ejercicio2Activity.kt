package com.example.serviciosweb.ejercicio2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.serviciosweb.R
import org.json.JSONObject

class Ejercicio2Activity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var btnEnviar: Button
    private lateinit var btnVolver: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio2)

        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        btnEnviar = findViewById(R.id.btnEnviar)
        btnVolver = findViewById(R.id.btnVolver)
        tvResultado = findViewById(R.id.tvResultado)

        btnEnviar.setOnClickListener {
            realizarPeticionPost()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun realizarPeticionPost() {
        val nombre = etNombre.text.toString().trim()
        val correo = etCorreo.text.toString().trim()

        if (nombre.isEmpty() || correo.isEmpty()) {
            tvResultado.text = "Por favor ingrese nombre y correo."
            return
        }

        val url = "https://jsonplaceholder.typicode.com/posts"
        tvResultado.text = "Enviando datos..."

        // Crear objeto JSON con los datos a enviar
        val jsonBody = JSONObject().apply {
            put("title", nombre)
            put("body", correo)
            put("userId", 1)
        }

        val queue = Volley.newRequestQueue(this)

        // Petición POST usando JsonObjectRequest
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            { response ->
                try {
                    // Obtener el ID devuelto por el servidor
                    val id = response.optString("id", "Sin ID")
                    tvResultado.text = "Usuario creado con ID: $id\n\nRespuesta completa:\n$response"
                } catch (e: Exception) {
                    tvResultado.text = "Respuesta recibida pero error al parsear: ${e.message}"
                }
            },
            { error ->
                tvResultado.text = "Error en la petición POST: ${error.message}"
            }
        )

        queue.add(jsonObjectRequest)
    }
}
