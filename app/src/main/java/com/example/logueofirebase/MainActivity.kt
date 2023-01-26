package com.example.logueofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.logueofirebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding //Diferente
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()

    }
    fun crearNuevoUsuario(email:String, clave: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, clave)
            .addOnCompleteListener {
                if(it.isSuccessful){
                 // Por hacer   abrirPerfil()
                }
                else{
                    // Por hacer   showErrorAlert()
                }

            }
    }
    fun iniciarSesion(email:String,clave: String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,clave).addOnCompleteListener {
            if(it.isSuccessful){
                // Por hacer    abrirPerfil()
            }else{
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser!=null){
            startActivity(Intent(this,PerfilActivity::class.java))
        }
    }
}