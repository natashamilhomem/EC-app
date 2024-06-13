package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ObrasCadastradasAdm : AppCompatActivity() {

    private lateinit var obraAdapter: ObraAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_obras_cadastradas_adm)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        Firebase.firestore.collection("obras").get().addOnSuccessListener{docs->
            val list = docs.map { doc ->
                val data = doc.data
                data["id"] = doc.id
                data
            }
            Log.d("list", list.toString())

            obraAdapter.updateDataSet(list)
        }

        obraAdapter = ObraAdapter(emptyList(), isAdmin = true)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = obraAdapter

        val cadastrarNovaObra = findViewById<Button>(R.id.btnCadastrar)
        cadastrarNovaObra.setOnClickListener {
            val i = Intent(this, TelaCRUDAdm::class.java)
            startActivity(i)
        }



    }
}