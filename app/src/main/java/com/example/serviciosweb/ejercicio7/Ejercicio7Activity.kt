package com.example.serviciosweb.ejercicio7

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkError
import com.android.volley.NoConnectionError
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.ServerError
import com.android.volley.TimeoutError
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.serviciosweb.R
import org.json.JSONObject

class Ejercicio7Activity : AppCompatActivity() {

    private lateinit var btnGet: Button
    private lateinit var btnPost: Button
    private lateinit var btnPut: Button
    private lateinit var btnDelete: Button
    private lateinit var btnVolver: Button
    private lateinit var tvResultado: TextView

    // URL usando localhost con adb reverse (puerto redirigido al computador)
    private val urlBase = "http://localhost:3000/libros"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio7)

        btnGet = findViewById(R.id.btnGet)
        btnPost = findViewById(R.id.btnPost)
        btnPut = findViewById(R.id.btnPut)
        btnDelete = findViewById(R.id.btnDelete)
        btnVolver = findViewById(R.id.btnVolver)
        tvResultado = findViewById(R.id.tvResultado)

        btnGet.setOnClickListener { realizarGet() }
        btnPost.setOnClickListener { realizarPost() }
        btnPut.setOnClickListener { realizarPut() }
        btnDelete.setOnClickListener { realizarDelete() }

        btnVolver.setOnClickListener { finish() }
    }

    private fun obtenerMensajeError(error: VolleyError): String {
        return when (error) {
            is TimeoutError -> "Error: Tiempo de espera agotado (Timeout)."
            is NoConnectionError -> "Error: Sin conexión. Asegúrate de ejecutar 'adb reverse tcp:3000 tcp:3000'."
            is ServerError -> "Error: El servidor respondió con error (5xx)."
            is NetworkError -> "Error de red."
            is ParseError -> "Error al procesar la respuesta JSON."
            else -> "Error: ${error.message ?: error.javaClass.simpleName}"
        }
    }

    private fun aplicarRetryPolicy(request: Request<*>) {
        request.retryPolicy = DefaultRetryPolicy(
            15000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
    }

    private fun realizarGet() {
        tvResultado.text = "Consultando libros..."
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, urlBase,
            { response ->
                tvResultado.text = "GET Exitoso:\n$response"
            },
            { error ->
                tvResultado.text = obtenerMensajeError(error)
            }
        )
        aplicarRetryPolicy(stringRequest)
        queue.add(stringRequest)
    }

    private fun realizarPost() {
        tvResultado.text = "Agregando libro..."
        val queue = Volley.newRequestQueue(this)

        val jsonBody = JSONObject().apply {
            put("titulo", "Nuevo Libro Android")
            put("autor", "Estudiante SENA")
            put("paginas", 250)
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, urlBase, jsonBody,
            { response ->
                tvResultado.text = "POST Exitoso (Libro agregado):\n$response"
            },
            { error ->
                tvResultado.text = obtenerMensajeError(error)
            }
        )
        aplicarRetryPolicy(jsonObjectRequest)
        queue.add(jsonObjectRequest)
    }

    private fun realizarPut() {
        val urlPut = "$urlBase/1"
        tvResultado.text = "Actualizando libro ID 1..."
        val queue = Volley.newRequestQueue(this)

        val jsonBody = JSONObject().apply {
            put("titulo", "Libro Actualizado (PUT)")
            put("autor", "Autor Modificado")
            put("paginas", 300)
        }

        val stringRequest = object : StringRequest(
            Method.PUT, urlPut,
            { response ->
                tvResultado.text = "PUT Exitoso:\n$response"
            },
            { error ->
                tvResultado.text = obtenerMensajeError(error)
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
            override fun getBody(): ByteArray {
                return jsonBody.toString().toByteArray(Charsets.UTF_8)
            }
        }
        aplicarRetryPolicy(stringRequest)
        queue.add(stringRequest)
    }

    private fun realizarDelete() {
        val urlDelete = "$urlBase/1"
        tvResultado.text = "Eliminando libro ID 1..."
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.DELETE, urlDelete,
            { response ->
                tvResultado.text = "DELETE Exitoso (Libro ID 1 eliminado):\n$response"
            },
            { error ->
                tvResultado.text = obtenerMensajeError(error)
            }
        )
        aplicarRetryPolicy(stringRequest)
        queue.add(stringRequest)
    }
}
