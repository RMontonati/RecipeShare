package com.example.recipeshare

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.recipeshare.databinding.ActivityMainBinding
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private lateinit var binder : ActivityMainBinding
    private lateinit var database : DatabaseReference
    private var existing : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)



        binder.btnStart.setOnClickListener{
            signIn()
        }

        binder.btnLogin.setOnClickListener{
            logIn()
        }

    }

    private fun signIn(){
        val nome = binder.userName.text.toString()
        val password = binder.password.text.toString()
        if (nome.isEmpty()){
            Toast.makeText(this, "Inserisci il tuo nome", Toast.LENGTH_SHORT).show()
        }
        else {
            if (password.isNotEmpty()){
                database = FirebaseDatabase.getInstance().getReference("Utenti")
                database.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (users in snapshot.children) {
                            val us = users.getValue(Utenti::class.java)
                            val uName = us?.username.toString()
                            if (us != null) {
                                if (uName == nome) {
                                    existing = true
                                    break
                                }
                            }
                        }


                        if (!existing) {
                            val utente = Utenti(nome, password)
                            database.child(nome).setValue(utente).addOnSuccessListener {
                                Toast.makeText(this@MainActivity, "Benvenuto ${nome}", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Qualcosa è andato storto!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            database = FirebaseDatabase.getInstance().getReference("Recipes")

                            val intent = Intent(this@MainActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            existing = false
                            println("Sono qui")
                            return
                        }

                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.d(ContentValues.TAG, error.message)
                    }
                })

            }
        }

    }

    private fun logIn(){
        val nome = binder.userName.text.toString()
        val password = binder.password.text.toString()
        if (nome.isEmpty()){
            Toast.makeText(this, "Inserisci il tuo nome", Toast.LENGTH_SHORT).show()
        }
        else {
            if (password.isNotEmpty()){
                database = FirebaseDatabase.getInstance().getReference("Utenti")
                database.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (users in snapshot.children) {
                            val user = users.getValue(Utenti::class.java)
                            if (user != null) {
                                if (user.username == nome && user.password == password) {
                                    existing = true
                                    break
                                }
                            }
                        }
                        if (existing) {
                            existing = false
                            Toast.makeText(this@MainActivity, "Bentornato ${nome}", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@MainActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@MainActivity, "Credenziali insesistenti.", Toast.LENGTH_SHORT).show()
                            return
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }
        }

    }

}




