package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class telaInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_info)

        findViewById<Button>(R.id.bt_obras).setOnClickListener{
            irParaTelaObras()
        }

        findViewById<Button>(R.id.bt_home).setOnClickListener{
            irParaTelaHome()
        }

        findViewById<Button>(R.id.bt_login).setOnClickListener{
            irParaTelaLogin()
        }

    }

    fun irParaTelaObras() {
        Log.d("Tela Info", "Ir para tela de Obras")
        val telaObras = Intent(this, telaObras::class.java)
        startActivity(telaObras)
    }

    fun irParaTelaHome() {
        Log.d("Tela Info", "Ir para Home")
        val telaHome = Intent(this, MainActivity::class.java)
        startActivity(telaHome)
    }

    fun irParaTelaLogin() {
        Log.d("MainActivity", "Ir para tela de Login")
        val telaLogin = Intent(this, telaLoginUsuario::class.java)
        startActivity(telaLogin)
    }
}