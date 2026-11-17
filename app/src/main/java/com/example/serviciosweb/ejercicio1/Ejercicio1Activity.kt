package com.example.serviciosweb.ejercicio1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.serviciosweb.R
import org.json.JSONArray

class Ejercicio1Activity : AppCompatActivity() {

    private lateinit var btnObtener: Button
    private lateinit var btnVolver: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio1)

        btnObtener = findViewById(R.id.btnObtener)
        btnVolver = findViewById(R.id.btnVolver)
        tvResultado = findViewById(R.id.tvResultado)

        btnObtener.setOnClickListener {
            realizarPeticionGet()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun realizarPeticionGet() {
        val url = "https://jsonplaceholder.typicode.com/users"
        tvResultado.text = "Cargando usuarios..."

        // Crear cola de peticiones con Volley
        val queue = Volley.newRequestQueue(this)

        // Petición GET usando StringRequest
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    // Procesar la respuesta JSON como JSONArray
                    val jsonArray = JSONArray(response)
                    val nombresBuilder = StringBuilder()

                    for (i in 0 until jsonArray.length()) {
                        val usuario = jsonArray.getJSONObject(i)
                        val nombre = usuario.getString("name")
                        nombresBuilder.append("• ").append(nombre).append("\n")
                    }

                    tvResultado.text = nombresBuilder.toString()
                } catch (e: Exception) {
                    tvResultado.text = "Error al procesar el JSON: ${e.message}"
                }
            },
            { error ->
                tvResultado.text = "Error en la petición: ${error.message}"
            }
        )

        // Añadir la petición a la cola
        queue.add(stringRequest)
    }
}
