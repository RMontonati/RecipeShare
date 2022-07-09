package com.example.recipeshare.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeshare.R
import com.example.recipeshare.Ricette
import kotlinx.android.synthetic.main.home_elements.view.*
import kotlinx.android.synthetic.main.home_sub_elements.view.*

class SubAdapter:RecyclerView.Adapter<SubAdapter.CardViewHolder>() {

    var ctx : Context? = null
    var arraySubCategory = ArrayList<Ricette>()
    var listener : SubAdapter.OnItemClickListener? = null

    class CardViewHolder(view: View): RecyclerView.ViewHolder(view){


    }

    fun setData(arrData : List<Ricette>){
        arraySubCategory = arrData as ArrayList<Ricette>
    }

    fun setClickListener(listener1 : SubAdapter.OnItemClickListener){
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        ctx = parent.context
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_sub_elements,
            parent, false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        holder.itemView.cover.setImageResource(arraySubCategory[position].image!!)
        holder.itemView.placeholder.text = arraySubCategory[position].titolo
        //Glide.with(ctx!!).load(arraySubCategory[position].image).into(holder.itemView.image_dish)

        holder.itemView.rootView.setOnClickListener{
            listener!!.onClicked(arraySubCategory[position])
        }
    }

    override fun getItemCount(): Int {
        return arraySubCategory.size
    }

    interface OnItemClickListener{
        fun onClicked(data : Ricette)
    }

}