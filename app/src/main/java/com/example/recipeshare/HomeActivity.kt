package com.example.recipeshare

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeshare.Adapter.MainAdapter
import com.example.recipeshare.Adapter.SubAdapter
import com.example.recipeshare.databinding.ActivityHomeBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var mainAdapter = MainAdapter()
    private var subAdapter = SubAdapter()
    private lateinit var arrAntipasti : ArrayList<Ricette>
    private lateinit var arrSecondi : ArrayList<Ricette>
    private lateinit var arrDessert : ArrayList<Ricette>
    private lateinit var binding: ActivityHomeBinding
    private lateinit var database : DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        arrAntipasti = arrayListOf()
        arrPrimi = arrayListOf()
        arrSecondi = arrayListOf()
        arrDessert = arrayListOf()
        database = FirebaseDatabase.getInstance().getReference("Recipes")
        populateArray()
        Handler().postDelayed({

            populateHome()

        mainAdapter.setData(mainCategoryList)
        subAdapter.setData(arrPrimi)
        mainAdapter.setClickListener(onClickedMain)
        subAdapter.setClickListener(onClicked)

        rv_main.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        rv_main.adapter = mainAdapter

        rv_sub.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        rv_sub.adapter = subAdapter

        binding.searchView.setOnClickListener{
            startActivity(Intent(this, SearchActivity::class.java))
        }

        },500)
    }


    private val onClickedMain = object :MainAdapter.OnItemClickListener{

        override fun onClickedMain(data: String) {
            if(data == "Primi piatti") {
                subAdapter.setData(arrPrimi)
                rv_sub.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                rv_sub.adapter = subAdapter
            }
            if(data == "Secondi") {
                subAdapter.setData(arrSecondi)
                rv_sub.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                rv_sub.adapter = subAdapter
            }
            if(data == "Dessert") {
                subAdapter.setData(arrDessert)
                rv_sub.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                rv_sub.adapter = subAdapter
            }
            if(data == "Antipasti") {
                subAdapter.setData(arrAntipasti)
                rv_sub.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                rv_sub.adapter = subAdapter
            }
        }

    }

    private val onClicked = object :SubAdapter.OnItemClickListener{
        override fun onClicked(data: Ricette) {

            val titolo = data.titolo
            val image = data.image
            val categoria = data.categoria
            val descrizione = data.descrizione
            val valutazione = data.valutazione
            val count = data.count
            val id = data.id

            val intent = Intent(this@HomeActivity, RecipeActivity::class.java)
            intent.putExtra("titolo", titolo)
            intent.putExtra("image", image)
            intent.putExtra("categoria", categoria)
            intent.putExtra("descrizione", descrizione)
            intent.putExtra("valutazione", valutazione)
            intent.putExtra("count", count)
            intent.putExtra("id", id)
            startActivity(intent)
        }


    }


    private fun populateArray() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (ds in snapshot.children) {
                    val recipe = ds.getValue(Ricette::class.java)
                    val categoryName = recipe?.categoria.toString()
                    if(categoryName == "Primi piatti"){
                        arrPrimi.add(recipe!!)
                    }
                    if(categoryName == "Secondi"){
                        arrSecondi.add(recipe!!)
                    }
                    if(categoryName == "Dessert"){
                        arrDessert.add(recipe!!)
                    }
                    if(categoryName == "Antipasti"){
                        arrAntipasti.add(recipe!!)
                    }
                    Log.d(ContentValues.TAG, categoryName!!)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.message)
            }
        })
    }

    private fun populateHome(){

        mainCategoryList.clear()
        val primi = Categorie(
            R.drawable.primary_dish,
            "Primi piatti"
        )

        mainCategoryList.add(primi)

        val secondi = Categorie(
            R.drawable.secondi_piatti,
            "Secondi"
        )

        mainCategoryList.add(secondi)

        val dessert = Categorie(
            R.drawable.dessert,
            "Dessert"
        )

        mainCategoryList.add(dessert)

        val antipasti = Categorie(
            R.drawable.tortasalata_img,
            "Antipasti"
        )

        mainCategoryList.add(antipasti)

    }

}