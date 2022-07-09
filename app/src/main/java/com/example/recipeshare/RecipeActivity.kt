package com.example.recipeshare

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.recipeshare.databinding.ActivityRecipeBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_recipe.*


class RecipeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecipeBinding
    private lateinit var database : DatabaseReference

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val title = intent.getStringExtra("titolo")
        val pic = intent.getIntExtra("image", R.drawable.arrosto_img)
        val cat = intent.getStringExtra("categoria")
        val description = intent.getStringExtra("descrizione")
        var ratings = intent.getIntExtra("valutazione",0)
        var count = intent.getIntExtra("count",0)
        val iD = intent.getIntExtra("id", 0)
        var valutazione : Int


        binding.dishTitle.text = title
        binding.imagePlaceholder.setImageResource(pic)
        binding.descriptionPlaceholder.text = description!!
        if(count != 0) {
            binding.ratingPlaceholder.text = (ratings.toDouble() / count.toDouble()).toString()
        }
        else
            binding.ratingPlaceholder.text = "Non ci sono ancora valutazioni."

        back_arrow.setOnClickListener{
            finish()
        }

        rate_btn.setOnClickListener{
            val adb = AlertDialog.Builder(this@RecipeActivity)
            val voti = arrayOf("1","2","3","4","5")
            adb.setTitle("Che valutazione dai a questa ricetta?")
            adb.setSingleChoiceItems(voti,-1){dialogInterface :DialogInterface, i: Int ->
                 valutazione = voti[i].toInt()
                 database = FirebaseDatabase.getInstance().getReference("Recipes")
                 count += 1
                 ratings += valutazione
                 val ricetta = Ricette(pic, title, cat, description, ratings, count, iD)
                 database.child(title!!).setValue(ricetta)
            }
            adb.setPositiveButton("OK"){dialogInterface: DialogInterface, wich: Int ->
                 Toast.makeText(this, "Valutazione avvenuta con successo!", Toast.LENGTH_SHORT).show()
                 dialogInterface.cancel()
            }
            val mDialog = adb.create()
            mDialog.show()
        }

        btn_share.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, description)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Condividi ricetta: "))
        }

    }
}