package com.example.logueofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.logueofirebase.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.rpc.context.AttributeContext.Auth

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

    companion object{
        private const val RC_SIGN_IN=423
    }

    fun iniciarSesionGoogle(){
        val providerGoogle = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser!=null){
            startActivity(Intent(this,PerfilActivity::class.java))
        }
    }
}