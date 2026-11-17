package com.example.serviciosweb.ejercicio6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.serviciosweb.R

class Ejercicio6Activity : AppCompatActivity() {

    private lateinit var rvPersonajes: RecyclerView
    private lateinit var btnVolver: Button
    private val listaPersonajes = mutableListOf<Personaje>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio6)

        rvPersonajes = findViewById(R.id.rvPersonajes)
        btnVolver = findViewById(R.id.btnVolver)

        rvPersonajes.layoutManager = LinearLayoutManager(this)

        btnVolver.setOnClickListener {
            finish()
        }

        cargarPersonajes()
    }

    private fun cargarPersonajes() {
        val url = "https://rickandmortyapi.com/api/character"
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val results = response.getJSONArray("results")
                    listaPersonajes.clear()

                    for (i in 0 until results.length()) {
                        val item = results.getJSONObject(i)
                        val name = item.getString("name")
                        val species = item.getString("species")
                        val image = item.getString("image")

                        listaPersonajes.add(Personaje(name, species, image))
                    }

                    val adapter = PersonajeAdapter(listaPersonajes) { personaje ->
                        // Al tocar un elemento, abrir DetalleEjercicio6Activity
                        val intent = Intent(this, DetalleEjercicio6Activity::class.java).apply {
                            putExtra("NOMBRE", personaje.name)
                            putExtra("ESPECIE", personaje.species)
                            putExtra("IMAGEN", personaje.image)
                        }
                        startActivity(intent)
                    }

                    rvPersonajes.adapter = adapter

                } catch (e: Exception) {
                    Toast.makeText(this, "Error al procesar JSON: ${e.message}", Toast.LENGTH_LONG).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error de red: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )

        queue.add(jsonObjectRequest)
    }
}
