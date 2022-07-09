package com.example.recipeshare.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.recipeshare.R
import com.example.recipeshare.Ricette


class MyAdapter(private val context: Activity, private val arrayList : ArrayList<Ricette>): ArrayAdapter<Ricette>(context,
                    R.layout.list_item,arrayList) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item, null)

        val imageView : ImageView = view.findViewById(R.id.recipe_pic)
        val title : TextView = view.findViewById(R.id.recipe_title)

        arrayList[position].image?.let { imageView.setImageResource(it) }
        title.text = arrayList[position].titolo

        return view
    }

}