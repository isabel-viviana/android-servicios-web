package com.example.serviciosweb.ejercicio6

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.example.serviciosweb.R

class DetalleEjercicio6Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_ejercicio6)

        val ivDetalle = findViewById<ImageView>(R.id.ivDetalle)
        val tvDetalleNombre = findViewById<TextView>(R.id.tvDetalleNombre)
        val tvDetalleEspecie = findViewById<TextView>(R.id.tvDetalleEspecie)
        val btnVolverDetalle = findViewById<Button>(R.id.btnVolverDetalle)

        val nombre = intent.getStringExtra("NOMBRE") ?: "Desconocido"
        val especie = intent.getStringExtra("ESPECIE") ?: "Desconocida"
        val imagenUrl = intent.getStringExtra("IMAGEN") ?: ""

        tvDetalleNombre.text = nombre
        tvDetalleEspecie.text = "Especie: $especie"

        if (imagenUrl.isNotEmpty()) {
            val imageRequest = ImageRequest(
                imagenUrl,
                { bitmap ->
                    ivDetalle.setImageBitmap(bitmap)
                },
                0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                { _ -> }
            )
            Volley.newRequestQueue(this).add(imageRequest)
        }

        btnVolverDetalle.setOnClickListener {
            finish()
        }
    }
}
