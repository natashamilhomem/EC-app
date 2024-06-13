package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.bt_login).setOnClickListener{
            irParaTelaLogin()
        }

        findViewById<Button>(R.id.bt_info).setOnClickListener{
            irParaTelaInfo()
        }

        findViewById<Button>(R.id.bt_obras).setOnClickListener{
            irParaTelaObras()
        }

        findViewById<Button>(R.id.bt_eventos).setOnClickListener{
            irParaTelaEventos()
        }
    }

    fun irParaTelaLogin() {
        Log.d("MainActivity", "Ir para tela de Login")
        val telaLogin = Intent(this, telaLoginUsuario::class.java)
        startActivity(telaLogin)
    }

    fun irParaTelaInfo() {
        Log.d("MainActivity", "Ir para tela de Informações")
        val telaInformacao = Intent(this, telaInfo::class.java)
        startActivity(telaInformacao)
    }

    fun irParaTelaObras() {
        Log.d("MainActivity", "Ir para tela de Obras")
        val telaObras = Intent(this, telaObras::class.java)
        startActivity(telaObras)
    }

    fun irParaTelaEventos() {
        Log.d("MainActivity", "Ir para tela de Eventos")
        val eventos = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.unifor.br/espaco-cultural-unifor#tabs"))
        startActivity(eventos)
    }

    /*
    Firebase.firestore.collection("obras").add(mapOf(
        "nome" to "nomeobra2",
        "autor" to "autor",
        "data" to "datahoje"
    ))

    Firebase.firestore.collection("obras")
        .document("wVne7ij8yFzn63NJww0o").get().result.data
    */
}
