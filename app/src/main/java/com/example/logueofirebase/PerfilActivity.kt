package com.example.logueofirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.logueofirebase.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PerfilActivity : AppCompatActivity() {
    var auth:FirebaseAuth=FirebaseAuth.getInstance()
    var user: FirebaseUser?=auth.currentUser
    private lateinit var binding: ActivityPerfilBinding //Diferente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var btnResetClave =binding.btnRestablecer
        var btnDeleteUser=binding.delete
        if(user!=null){
            setup(user?.email.toString())
        }

        btnResetClave.setOnClickListener {
            auth.sendPasswordResetEmail(user?.email.toString()).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,this.resources.getString(R.string.msg_restablecerclave),Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
        btnDeleteUser.setOnClickListener {
            user?.delete()?.addOnCompleteListener {
                if(it.isSuccessful){
                    startActivity(Intent(this,MainActivity::class.java))

                    Toast.makeText(this,this.resources.getString(R.string.msg_borraruser),Toast.LENGTH_SHORT).show()//
                }

            }
        }


    }
    fun setup(email:String){
        val btnCerrar=binding.btnCerrar
        var titulo = binding.tituloText
        var correo = binding.correoText
        var usuario = binding.usuarioText
        var nacionalidad = binding.nacionalidadText
        var edad = binding.edadText
        val db = Firebase.firestore

        db.collection("user").document(user?.email.toString()).get().addOnSuccessListener {
            documento ->
            val email = documento.getString("email")
            val user = documento.getString("usuario")
            val nacionality = documento.getString("nacionalidad")
            val age = documento.getString("edad")

            titulo.text=titulo.text.toString()+" "+user
            correo.text=email
            usuario.text=user
            nacionalidad.text=nacionality
            edad.text=age
        }


        btnCerrar.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
        }


    }
}