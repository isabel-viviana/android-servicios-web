package com.example.serviciosweb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.serviciosweb.ejercicio1.Ejercicio1Activity
import com.example.serviciosweb.ejercicio2.Ejercicio2Activity
import com.example.serviciosweb.ejercicio3.Ejercicio3Activity
import com.example.serviciosweb.ejercicio4.Ejercicio4Activity
import com.example.serviciosweb.ejercicio5.Ejercicio5Activity
import com.example.serviciosweb.ejercicio6.Ejercicio6Activity
import com.example.serviciosweb.ejercicio7.Ejercicio7Activity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuración de botones del menú principal para abrir cada ejercicio
        findViewById<Button>(R.id.btnEjercicio1).setOnClickListener {
            startActivity(Intent(this, Ejercicio1Activity::class.java))
        }

        findViewById<Button>(R.id.btnEjercicio2).setOnClickListener {
            startActivity(Intent(this, Ejercicio2Activity::class.java))
        }

        findViewById<Button>(R.id.btnEjercicio3).setOnClickListener {
            startActivity(Intent(this, Ejercicio3Activity::class.java))
        }

        findViewById<Button>(R.id.btnEjercicio4).setOnClickListener {
            startActivity(Intent(this, Ejercicio4Activity::class.java))
        }

        findViewById<Button>(R.id.btnEjercicio5).setOnClickListener {
            startActivity(Intent(this, Ejercicio5Activity::class.java))
        }

        findViewById<Button>(R.id.btnEjercicio6).setOnClickListener {
            startActivity(Intent(this, Ejercicio6Activity::class.java))
        }

        findViewById<Button>(R.id.btnEjercicio7).setOnClickListener {
            startActivity(Intent(this, Ejercicio7Activity::class.java))
        }
    }
}
