package com.example.recipeshare

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.*
import com.example.recipeshare.Adapter.MyAdapter
import com.example.recipeshare.databinding.ActivitySearchBinding
import com.google.firebase.database.*


class SearchActivity : AppCompatActivity() {


    private lateinit var database : DatabaseReference
    private lateinit var binding : ActivitySearchBinding
    private lateinit var nome : String
    private lateinit var tempArray : ArrayList<Ricette>
    private val recipeArray = arrayListOf<Ricette>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        tempArray = arrayListOf()
        database = FirebaseDatabase.getInstance().getReference("Recipes")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    var model = data.getValue(Ricette::class.java)
                    if (model != null) {
                        nome = model.titolo.toString()
                        recipeArray.add(model)
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(ContentValues.TAG, error.message)
            }

        })


        Handler().postDelayed({


        tempArray.addAll(recipeArray)
        binding.listView.adapter = MyAdapter(this, tempArray)
        binding.listView.isClickable = true
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "indietro"


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                for(ricette in recipeArray){
                    if (query?.let { ricette.titolo?.contains(it) } == true){
                        (binding.listView.adapter as MyAdapter).filter.filter(query)
                        (binding.listView.adapter as MyAdapter).notifyDataSetChanged()
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempArray.clear()
                val searchText = newText!!.lowercase()
                if(searchText.isNotEmpty()){

                    recipeArray.forEach{
                        if(it.titolo!!.lowercase().contains(searchText))
                            tempArray.add(it)
                    }

                    (binding.listView.adapter as MyAdapter).notifyDataSetChanged()

                }
                else{
                    tempArray.clear()
                    tempArray.addAll(recipeArray)
                    (binding.listView.adapter as MyAdapter).notifyDataSetChanged()
                }

                return false
            }

        })

    }, 500)


        binding.listView.setOnItemClickListener{ parent, view, position, id ->

            val titolo = tempArray[position].titolo
            val image = tempArray[position].image
            val categoria = tempArray[position].categoria
            val descrizione = tempArray[position].descrizione
            val valutazione = tempArray[position].valutazione
            val conteggio = tempArray[position].count
            val id = tempArray[position].id

            val intent = Intent(this, RecipeActivity::class.java)
            intent.putExtra("titolo", titolo)
            intent.putExtra("image", image)
            intent.putExtra("categoria", categoria)
            intent.putExtra("descrizione", descrizione)
            intent.putExtra("valutazione", valutazione)
            intent.putExtra("count", conteggio)
            intent.putExtra("id", id)
            startActivity(intent)

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}