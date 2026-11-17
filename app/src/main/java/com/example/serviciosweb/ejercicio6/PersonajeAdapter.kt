package com.example.serviciosweb.ejercicio6

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.example.serviciosweb.R

class PersonajeAdapter(
    private val listaPersonajes: List<Personaje>,
    private val onItemClick: (Personaje) -> Unit
) : RecyclerView.Adapter<PersonajeAdapter.PersonajeViewHolder>() {

    class PersonajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivItem: ImageView = view.findViewById(R.id.ivItem)
        val tvItemNombre: TextView = view.findViewById(R.id.tvItemNombre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_personaje, parent, false)
        return PersonajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonajeViewHolder, position: Int) {
        val personaje = listaPersonajes[position]
        holder.tvItemNombre.text = personaje.name

        // Cargar imagen con Volley ImageRequest
        val context = holder.itemView.context
        val imageRequest = ImageRequest(
            personaje.image,
            { bitmap ->
                holder.ivItem.setImageBitmap(bitmap)
            },
            0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
            { _ ->
                holder.ivItem.setImageResource(android.R.drawable.ic_menu_report_image)
            }
        )
        Volley.newRequestQueue(context).add(imageRequest)

        holder.itemView.setOnClickListener {
            onItemClick(personaje)
        }
    }

    override fun getItemCount(): Int = listaPersonajes.size
}
