package com.example.logueofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.logueofirebase.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class PerfilActivity : AppCompatActivity() {
    var auth:FirebaseAuth=FirebaseAuth.getInstance()
    var user: FirebaseUser?=auth.currentUser
    private lateinit var binding: ActivityPerfilBinding //Diferente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(user!=null){
            setup(user?.email.toString())
        }
    }
    fun setup(email:String){
        var correo=binding.btnCorreo
        val btnCerrar=binding.btnCerrar
        correo.text=email
        btnCerrar.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
        }


    }
}