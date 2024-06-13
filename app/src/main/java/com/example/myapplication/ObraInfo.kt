package com.example.myapplication

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ObraInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_obra_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val titulo = findViewById<TextView>(R.id.textTituloDetail)
        val descricao = findViewById<TextView>(R.id.textDescricaoDetail)
        val autor = findViewById<TextView>(R.id.textAutorDetail)
        val imageObra = findViewById<ImageView>(R.id.imageObraDetail)

        val obraId = intent.getStringExtra("obraId")
        if(obraId != null) {
            Firebase.firestore.collection("obras").document(obraId).get().addOnSuccessListener { doc ->
                titulo.text = Editable.Factory.getInstance().newEditable(doc["titulo"] as String)
                descricao.text = Editable.Factory.getInstance().newEditable(doc["desc"] as String)
                autor.text = Editable.Factory.getInstance().newEditable(doc["autor"] as String)
                getImage(doc["img"] as String?, imageObra)
            }
        }
    }

    fun getImage(base64String: String?, imageView: ImageView) {
        if(base64String.isNullOrEmpty()) return
        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        imageView.setImageBitmap(decodedImage)
    }
}