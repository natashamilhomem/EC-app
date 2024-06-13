package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TelaCRUDAdm : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = Firebase.firestore

    private val PICK_IMAGE_REQUEST = 1
    private var  bas64img:String = ""

    private var ehAtualizacao = false
    private var obraId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_crudadm)

        findViewById<Button>(R.id.buttonUpload).setOnClickListener {
            selectImage()
        }
        var btnSalvar = findViewById<Button>(R.id.buttonSalvar)
        var titulo = findViewById<EditText>(R.id.editTitulo)
        var descricao = findViewById<EditText>(R.id.editDescricao)
        var nomeAutor = findViewById<EditText>(R.id.editNomeAutor)
        var btnExcluir = findViewById<Button>(R.id.buttonExcluir)
        var img = findViewById<ImageView>(R.id.imageView)

        val obraId = intent.getStringExtra("obraId")
        if(obraId != null) {
            this.obraId = obraId
            ehAtualizacao = true
            btnExcluir.visibility = View.VISIBLE
            Firebase.firestore.collection("obras").document(obraId).get().addOnSuccessListener {  doc ->
                titulo.text = Editable.Factory.getInstance().newEditable(doc["titulo"] as String)
                descricao.text = Editable.Factory.getInstance().newEditable(doc["desc"] as String)
                nomeAutor.text = Editable.Factory.getInstance().newEditable(doc["autor"] as String)
                getImage(doc["img"] as String, img)
            }
        }

        btnSalvar.setOnClickListener{

           val map = mapOf(
                "titulo" to titulo.text.toString(),
                "desc" to descricao.text.toString(),
                "autor" to nomeAutor.text.toString(),
                "img" to bas64img
            )

            if(ehAtualizacao && obraId != null) {
                Firebase.firestore.collection("obras").document(obraId).update(map).addOnSuccessListener {
                    Log.d("Firebasefirestore", "DocumentSnapshot $obraId successfully updated!")
                    finish()
                }
            } else {
                Firebase.firestore.collection("obras").add(map)
                .addOnSuccessListener { doc ->
                    Log.d("Firebasefirestore", "DocumentSnapshot ${doc.id} successfully written!")
                    finish()
               }


            }
        }

        btnExcluir.setOnClickListener{
            if(ehAtualizacao && obraId != null) {
                Firebase.firestore.collection("obras").document(obraId).delete()
                    .addOnSuccessListener {
                        Log.d("Firebasefirestore", "DocumentSnapshot $obraId successfully deleted!")
                        finish()
                    }
            }
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            if (selectedImageUri != null) {
                findViewById<ImageView>(R.id.imageView).setImageURI(selectedImageUri)

                val inputStream = contentResolver.openInputStream(selectedImageUri)
                val bytes = inputStream?.readBytes()
                val base64Image = bytes?.let { encodeImageToBase64(it) }
                bas64img = base64Image.toString()
                if (base64Image != null) {
                    // Você pode usar a variável 'base64Image' conforme necessário
                    //Toast.makeText(this, "Imagem em Base64: $base64Image", Toast.LENGTH_SHORT).show()
                } else {
                    //Toast.makeText(this, "Falha ao converter imagem para Base64.", Toast.LENGTH_SHORT).show()
                }
            } else {
                //Toast.makeText(this, "Falha ao obter a imagem.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun encodeImageToBase64(image: ByteArray): String {
        return Base64.encodeToString(image, Base64.DEFAULT)
    }

    fun getImage(base64String: String, imageView: ImageView) {
        if(base64String.isEmpty()) return
        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        imageView.setImageBitmap(decodedImage)
    }
}
