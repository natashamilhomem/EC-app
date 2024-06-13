package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.common.base.Objects

class ObraAdapter (private var dataSet: List<Map<String, Any>>, val isAdmin: Boolean = false) :
    RecyclerView.Adapter<ObraAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitulo: TextView
        val textAutor: TextView
        val imageObra: ImageView
        val layout: LinearLayout

        init {
            // Define click listener for the ViewHolder's View
            textTitulo = view.findViewById(R.id.textTitulo)
            textAutor = view.findViewById(R.id.textAutor)
            imageObra = view.findViewById(R.id.imageObra)
            layout = view.findViewById(R.id.obra_item_layout)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.obra_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val item = dataSet[position]
        Log.d("item", item.toString())

        viewHolder.textTitulo.text = item["titulo"].toString()
        viewHolder.textAutor.text = item["autor"].toString()
        getImage(item["img"] as String?, viewHolder.imageObra)
        if(isAdmin) {
            viewHolder.layout.setOnClickListener {
                val i = Intent(viewHolder.itemView.context, TelaCRUDAdm::class.java)
                i.putExtra("obraId", item["id"] as String)
                viewHolder.itemView.context.startActivity(i)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataSet(newDataset: List<Map<String, Any>>) {
        dataSet = newDataset
        notifyDataSetChanged()
    }

    fun getImage(base64String: String?, imageView: ImageView) {
        if(base64String.isNullOrEmpty()) return
        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        imageView.setImageBitmap(decodedImage)
    }
}
