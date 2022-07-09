package com.example.recipeshare.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeshare.R
import com.example.recipeshare.Categorie
import kotlinx.android.synthetic.main.home_elements.view.*
import kotlinx.android.synthetic.main.home_sub_elements.view.*

class MainAdapter:RecyclerView.Adapter<MainAdapter.RecipeViewHolder>() {

    var arrayCategory = ArrayList<Categorie>()
    var listener : MainAdapter.OnItemClickListener? = null
    var ctx : Context? = null
    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view){

    }

    fun setData(arrData : List<Categorie>){
        arrayCategory = arrData as ArrayList<Categorie>
    }

    fun setClickListener(listener1 : MainAdapter.OnItemClickListener){
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_elements,
            parent, false))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        holder.itemView.image_dish.setImageResource(arrayCategory[position].cover)
        holder.itemView.dish_name.text = arrayCategory[position].descrizione
        holder.itemView.rootView.setOnClickListener{
            listener!!.onClickedMain(arrayCategory[position].descrizione)
        }

    }

    override fun getItemCount(): Int {
        return arrayCategory.size
    }

    interface OnItemClickListener{
        fun onClickedMain(categoryName : String)
    }

}