package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class telaLoginUsuario : AppCompatActivity() {

    // Hardcoded admin credentials
    private val adminEmail = "admin"
    private val adminPassword = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_login_usuario2)

        findViewById<Button>(R.id.bt_info).setOnClickListener{
            irParaTelaInfo()
        }

        findViewById<Button>(R.id.bt_obras).setOnClickListener{
            irParaTelaObras()
        }

        findViewById<Button>(R.id.bt_home).setOnClickListener{
            irParaTelaHome()
        }

        findViewById<Button>(R.id.EntrarAdm).setOnClickListener{
            irParaTelaCRUD()
        }



        val emailEditText = findViewById<EditText>(R.id.subEditTextTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.subEditTextTextPassword)
        val loginButton = findViewById<Button>(R.id.EntrarAdm)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginAdmin(email, password)
        }
    }

    private fun loginAdmin(email: String, password: String) {
        if (email == adminEmail && password == adminPassword) {
            irParaTelaADM()
        } else {
            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun irParaTelaCRUD() {
        Log.d("Tela de login", "Ir para tela CRUD de obras")
        val telaCRUD = Intent(this, TelaCRUDAdm::class.java)
        startActivity(telaCRUD)
    }

    private fun irParaTelaADM() {
        Log.d("Tela de login", "Ir para tela Obras Cadastradas")
        val telaObras = Intent(this, ObrasCadastradasAdm::class.java)
        startActivity(telaObras)
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

    fun irParaTelaHome() {
        Log.d("MainActivity", "Ir para tela de Linha do Tempo")
        val telaHome = Intent(this, MainActivity::class.java)
        startActivity(telaHome)
    }
}
