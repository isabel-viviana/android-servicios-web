package com.example.serviciosweb.ejercicio3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.serviciosweb.R
import org.json.JSONObject

class Ejercicio3Activity : AppCompatActivity() {

    private lateinit var btnPut: Button
    private lateinit var btnPatch: Button
    private lateinit var btnVolver: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio3)

        btnPut = findViewById(R.id.btnPut)
        btnPatch = findViewById(R.id.btnPatch)
        btnVolver = findViewById(R.id.btnVolver)
        tvResultado = findViewById(R.id.tvResultado)

        btnPut.setOnClickListener {
            realizarPut()
        }

        btnPatch.setOnClickListener {
            realizarPatch()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun realizarPut() {
        val url = "https://jsonplaceholder.typicode.com/posts/1"
        tvResultado.text = "Enviando PUT..."

        val jsonBody = JSONObject().apply {
            put("id", 1)
            put("title", "Título Reemplazado (PUT)")
            put("body", "Cuerpo reemplazado totalmente con PUT")
            put("userId", 1)
        }

        val queue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Method.PUT, url,
            { response ->
                tvResultado.text = "PUT Exitoso (Reemplazo total):\n$response"
            },
            { error ->
                tvResultado.text = "Error en PUT: ${error.message}"
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return jsonBody.toString().toByteArray(Charsets.UTF_8)
            }
        }

        queue.add(stringRequest)
    }

    private fun realizarPatch() {
        val url = "https://jsonplaceholder.typicode.com/posts/1"
        tvResultado.text = "Enviando PATCH..."

        val jsonBody = JSONObject().apply {
            put("title", "Título Modificado Parcialmente (PATCH)")
        }

        val queue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Method.PATCH, url,
            { response ->
                tvResultado.text = "PATCH Exitoso (Modificación parcial):\n$response"
            },
            { error ->
                tvResultado.text = "Error en PATCH: ${error.message}"
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return jsonBody.toString().toByteArray(Charsets.UTF_8)
            }
        }

        queue.add(stringRequest)
    }
}
