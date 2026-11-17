package com.example.serviciosweb.ejercicio5

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.serviciosweb.R

class Ejercicio5Activity : AppCompatActivity() {

    private lateinit var btnObtener: Button
    private lateinit var btnVolver: Button
    private lateinit var ivPersonaje: ImageView
    private lateinit var tvNombre: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio5)

        btnObtener = findViewById(R.id.btnObtener)
        btnVolver = findViewById(R.id.btnVolver)
        ivPersonaje = findViewById(R.id.ivPersonaje)
        tvNombre = findViewById(R.id.tvNombre)

        btnObtener.setOnClickListener {
            obtenerPersonaje()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun obtenerPersonaje() {
        val url = "https://rickandmortyapi.com/api/character"
        tvNombre.text = "Cargando personaje..."

        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val results = response.getJSONArray("results")
                    if (results.length() > 0) {
                        val character = results.getJSONObject(0)
                        val name = character.getString("name")
                        val species = character.getString("species")
                        val imageUrl = character.getString("image")

                        tvNombre.text = "Nombre: $name\nEspecie: $species"

                        // Descargar imagen usando ImageRequest de Volley
                        val imageRequest = ImageRequest(
                            imageUrl,
                            { bitmap ->
                                ivPersonaje.setImageBitmap(bitmap)
                            },
                            0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                            { error ->
                                tvNombre.text = "${tvNombre.text}\nError al cargar imagen: ${error.message}"
                            }
                        )

                        queue.add(imageRequest)
                    }
                } catch (e: Exception) {
                    tvNombre.text = "Error al procesar JSON: ${e.message}"
                }
            },
            { error ->
                tvNombre.text = "Error de conexión: ${error.message}"
            }
        )

        queue.add(jsonObjectRequest)
    }
}
