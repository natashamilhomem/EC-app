package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageAnalysis
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executors

class telaObras : AppCompatActivity() {

    private lateinit var obraAdapter: ObraAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_obras)

        obraAdapter = ObraAdapter(emptyList())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewObras)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = obraAdapter

        Firebase.firestore.collection("obras").get().addOnSuccessListener{ docs->
            val list = docs.map { doc ->
                val data = doc.data
                data["id"] = doc.id
                data
            }
            Log.d("list", list.toString())

            obraAdapter.updateDataSet(list)
        }

        findViewById<Button>(R.id.bt_info).setOnClickListener{
            irParaTelaInfo()
        }

        findViewById<Button>(R.id.bt_home).setOnClickListener{
            irParaTelaHome()
        }

        findViewById<Button>(R.id.bt_login).setOnClickListener{
            irParaTelaLogin()
        }

        findViewById<Button>(R.id.buttonQR).setOnClickListener{
            val i = Intent(this, QrScanner::class.java)
            startActivity(i)
        }

    }

    fun irParaTelaInfo() {
        Log.d("MainActivity", "Ir para tela de Informações")
        val telaInformacao = Intent(this, telaInfo::class.java)
        startActivity(telaInformacao)
    }

    fun irParaTelaHome() {
        Log.d("MainActivity", "Ir para tela de Linha do Tempo")
        val telaHome = Intent(this, MainActivity::class.java)
        startActivity(telaHome)
    }

    fun irParaTelaLogin() {
        Log.d("MainActivity", "Ir para tela de Login")
        val telaLogin = Intent(this, telaLoginUsuario::class.java)
        startActivity(telaLogin)
    }
}