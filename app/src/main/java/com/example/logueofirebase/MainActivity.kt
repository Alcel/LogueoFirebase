package com.example.logueofirebase

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.logueofirebase.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext.Auth
import org.checkerframework.checker.units.qual.Length

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding //Diferente
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        var correo = binding.editTextUsuario.text.toString()
        var clave = binding.editTextClave.text.toString()
        binding.buttonRegister.setOnClickListener {
            correo = binding.editTextUsuario.text.toString()
            clave = binding.editTextClave.text.toString()
            crearNuevoUsuario(correo,clave) }
        binding.imageButton.setOnClickListener { iniciarSesionGoogle() }
        binding.buttonLogIn.setOnClickListener {
            correo = binding.editTextUsuario.text.toString()
            clave = binding.editTextClave.text.toString()
            iniciarSesion(correo,clave) }

    }
    fun crearNuevoUsuario(email:String, clave: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, clave)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    writeNewUser(email)
                    startActivity(Intent(this,PerfilActivity::class.java))

                }
                else{
                    Toast.makeText(this,"Error al crear un nuevo usuario", Toast.LENGTH_SHORT)

                }
            }
    }
    fun iniciarSesion(email:String,clave: String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,clave).addOnCompleteListener {
            if(it.isSuccessful){
                startActivity(Intent(this,PerfilActivity::class.java))
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
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providerGoogle).build(),
        Companion.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Companion.RC_SIGN_IN){
            val response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser

                startActivity(Intent(this, PerfilActivity::class.java))
                finish()
            }
        }
    }

    fun writeNewUser(email:String){
        val db = Firebase.firestore
        val data = hashMapOf(
            "email" to email,
            "usuario" to "nouser",
            "nacionalidad" to "nonacionality",
            "edad" to "0"
        )
        db.collection("user").document(email).set(data).addOnSuccessListener {
            Log.d(ContentValues.TAG, "DocumentSnapshot succesfully written!")
        }.addOnFailureListener { e->Log.w(ContentValues.TAG,"Error writing document",e) }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser!=null){
            startActivity(Intent(this,PerfilActivity::class.java))
        }
    }
}