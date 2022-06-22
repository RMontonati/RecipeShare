package com.example.recipeshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_start = findViewById<Button>(R.id.btn_start)
        btn_start.setOnClickListener{
            val et_name = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.et_name)
            if (et_name.text.toString().isEmpty()){
                Toast.makeText(this, "Inserisci il tuo nome", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}