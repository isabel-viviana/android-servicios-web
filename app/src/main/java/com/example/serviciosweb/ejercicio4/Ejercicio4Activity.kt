package com.example.serviciosweb.ejercicio4

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.serviciosweb.R

class Ejercicio4Activity : AppCompatActivity() {

    private lateinit var btnEliminar: Button
    private lateinit var btnVolver: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio4)

        btnEliminar = findViewById(R.id.btnEliminar)
        btnVolver = findViewById(R.id.btnVolver)
        tvResultado = findViewById(R.id.tvResultado)

        btnEliminar.setOnClickListener {
            realizarDelete()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun realizarDelete() {
        val url = "https://jsonplaceholder.typicode.com/posts/1"
        tvResultado.text = "Eliminando usuario..."

        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.DELETE, url,
            { response ->
                // JSONPlaceholder devuelve un objeto vacío `{}` en DELETE exitoso
                tvResultado.text = "Usuario eliminado con éxito.\nRespuesta del servidor: $response"
            },
            { error ->
                tvResultado.text = "Error al eliminar usuario: ${error.message}"
            }
        )

        queue.add(stringRequest)
    }
}
